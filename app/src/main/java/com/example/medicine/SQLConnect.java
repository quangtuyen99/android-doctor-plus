package com.example.medicine;

import android.os.StrictMode;
import android.util.Log;

import com.example.medicine.Doctor.Doctor;
import com.example.medicine.Hospital.Hospital;
import com.example.medicine.Medicine.Medicine;
import com.example.medicine.Reminder.Reminder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLConnect {
    private static String ip = "172.16.0.148"; // cmd + ipconfig
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "QLThuoc"; // Tên database
    private static String username = "admin";
    private static String password = "admin";

    private static String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database;
    public static Connection connection = null;



    public static Connection setConnection() {

        // set Connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    // Tạo tài khoản
    public static boolean createAccount(String username, String pass, String phone, String name, String city) {
        if(connection != null) {
            Statement statement = null;
            try {
                String password = HashMD5.getMD5(pass);

                String query = "INSERT INTO NguoiDung VALUES ('" + username +
                        "', '" + password + "', '" +
                        phone + "', '" + name + "', '" +
                        city + "');"; // Phải để dấu '' ở giữa các text
                statement = connection.createStatement();
                statement.executeUpdate(query);
                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                return  false;
            }
        } else {
            Log.i("ABC", "Connection null");
        }
        return false;
    }


    // Kiểm tra tài khoản
    public static User checkAccount(String username, String pass) {
        if(connection != null) {
            Statement statement = null;
            try {
                String password = HashMD5.getMD5(pass);

                String query = "SELECT * FROM NguoiDung WHERE taiKhoan = '" + username +
                        "' AND matKhau = '" + password + "';"; // Phải để dấu '' ở giữa các text
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query);


                if (result.next()) {

                    return new User(result.getString("taiKhoan"), result.getString("matKhau"), result.getString("soDienThoai"),
                            result.getString("ten"), result.getString("thanhPho"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.i("ABC", "Connection null");
        }
        return null;
    }


    public static ArrayList<Medicine> getAllMedicine() {
        ArrayList<Medicine> medicines = new ArrayList<>();
        if(connection != null) {
            Statement statement = null;
            try {

                String query = "SELECT [idThuoc]\n" +
                        "      ,[tenThuoc]\n" +
                        "      ,[congTyCungCap]\n" +
                        "      ,[congDung]\n" +
                        "      ,[hanSuDung]\n" +
                        "      ,[congTyDangKy]\n" +
                        "      ,[soDangKy]\n" +
                        "      ,[hinhAnh], [loaiBaoChe]\n" +
                        "  FROM [dbo].[Thuoc] as t, [dbo].[BaoChe] as b\n" +
                        "  WHERE t.maBaoChe = b.idBaoChe";

                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Medicine medicine;
                    medicine = Medicine.setMedicine(Integer.parseInt(resultSet.getString("idThuoc")), resultSet.getString("tenThuoc"),
                            resultSet.getString("congTyCungCap"), resultSet.getString("congDung"), resultSet.getString("hanSuDung"),
                            resultSet.getString("congTyDangKy"), resultSet.getString("soDangKy"), resultSet.getString("hinhAnh"), resultSet.getString("loaiBaoChe"));
                    medicines.add(medicine);
                }

                return medicines;
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {
            Log.i("ABC", "Connection null");
        }

        return null;
    }


    // Tạo lời nhắc
    public static boolean saveReminder(String nameDrug, String nameSick, String dosage, String date, String time, String user) {
        if(connection != null) {
            Statement statement = null;
            try {

                String query = "INSERT INTO LoiNhac VALUES ('" + nameDrug +
                        "', '" + nameSick + "', '" +
                        dosage + "', '" + date + "', '" +
                        time + "', '" + user + "', 0 );"; // Phải để dấu '' ở giữa các text

                Log.i("BBB", query);
                statement = connection.createStatement();
                statement.executeUpdate(query);
                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            Log.i("ABC", "Connection null");
        }
        return false;
    }

    public static ArrayList<Reminder> getReminder(String id) {
        ArrayList<Reminder> reminders = new ArrayList<>();
        if(connection != null) {
            Statement statement = null;
            try {

                String query = "SELECT * FROM LoiNhac WHERE idNguoiDung = '" + id + "' and hoanThanh = 0;";

                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Reminder reminder = new Reminder( Integer.parseInt(resultSet.getString("idLoiNhac")), resultSet.getString("tenThuoc"), resultSet.getString("loaiBenh"), resultSet.getString("lieuLuong")
                            , resultSet.getString("ngayUong"), resultSet.getString("gioUong"), resultSet.getString("idNguoiDung"));

                    reminders.add(reminder);
                }

                return reminders;
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {
            Log.i("ABC", "Connection null");
        }

        return null;
    }


    // Chỉnh sửa lời nhắc
    public static void updateReminder(int id) {
        if(connection != null) {
            Statement statement = null;
            try {

                String query = "UPDATE LoiNhac set hoanThanh = 1 where idLoiNhac ='" + id + "';";

                statement = connection.createStatement();
                statement.executeUpdate(query);


            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {
            Log.i("ABC", "Connection null");
        }

    }


    public static void updateUser(String taikhoan, String sodienthoai, String matkhau, String ten, String thanhpho) {
        if(connection != null) {
            Statement statement = null;
            try {
                String password = HashMD5.getMD5(matkhau);
                String query = "UPDATE NguoiDung set soDienThoai = '" + sodienthoai + "', matKhau ='" + password + "', ten = '"
                        + ten + "', thanhPho = '" + thanhpho + "'  where taiKhoan ='" + taikhoan + "';";

                statement = connection.createStatement();
                statement.executeUpdate(query);


            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {
            Log.i("ABC", "Connection null");
        }
    }


    public static ArrayList<Hospital> getAllHospital() {
        ArrayList<Hospital> hospitals = new ArrayList<>();
        if(connection != null) {
            Statement statement = null;
            try {

                String query = "SELECT * FROM BenhVien";

                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Hospital hospital;
                    hospital = new Hospital(Integer.parseInt(resultSet.getString("idBenhVien")), resultSet.getString("ten"), resultSet.getString("diaChi"),
                            resultSet.getString("soDienThoai"), resultSet.getString("email"),
                            resultSet.getString("hinh"), resultSet.getString("viDo"), resultSet.getString("kinhDo"));
                    hospitals.add(hospital);
                }

                return hospitals;
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {
            Log.i("ABC", "Connection null");
        }

        return null;
    }

    public static ArrayList<Doctor> getAllDoctor(String chucvu) {
        ArrayList<Doctor> doctors = new ArrayList<>();
        if(connection != null) {
            Statement statement = null;
            try {

                String query = "SELECT * FROM BacSi WHERE chucvu LIKE N'" + chucvu + "';";

                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Doctor doctor = new Doctor(Integer.parseInt(resultSet.getString("idBacSi")), resultSet.getString("ten"),
                            resultSet.getString("chucVu"), resultSet.getString("kinhNghiem"), resultSet.getString("hinh"), resultSet.getString("book"),
                            resultSet.getString("soDienThoai"), resultSet.getString("diaChi"));

                    doctors.add(doctor);
                }

                return doctors;
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {
            Log.i("ABC", "Connection null");
        }

        return null;
    }
}
