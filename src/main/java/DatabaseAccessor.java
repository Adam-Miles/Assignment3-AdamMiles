/*
* Adam Miles
* 101266150
* COMP 3005 Assignment 3 part 1
*/

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class DatabaseAccessor{
    private Connection connection;
    public DatabaseAccessor(String url, String user, String password) throws Exception{
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);

        }
        catch(Exception e){
            throw e;
        }
    }

    public void getAllStudents() throws Exception{
        try{
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT * FROM students");
            ResultSet resultSet = statement.getResultSet();
            System.out.println("Students in the database:");
            while(resultSet.next()){
                System.out.print(resultSet.getInt("student_id")+" ");
                System.out.print(resultSet.getString("first_name")+" ");
                System.out.print(resultSet.getString("last_name")+" ");
                System.out.print(resultSet.getString("email")+" ");
                System.out.println(resultSet.getString("enrollment_date"));
            }
        }

        catch (Exception e){
            throw e;
        }
    }

    public void addStudent(String first_name, String last_name, String email, String enrollment_date) throws Exception{
        String insertCode = "INSERT INTO students (first_name, last_name, email, enrollment_date)  VALUES (?,?,?,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(insertCode);
            statement.setString(1,first_name);
            statement.setString(2,last_name);
            statement.setString(3,email);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            statement.setDate(4, new Date(dateFormat.parse(enrollment_date).getTime()));
            statement.executeUpdate();
            System.out.println("Student added successfully.");

        }
        catch (Exception e){
            throw e;
        }
    }

    public void updateStudentEmail(int student_id, String new_email) throws Exception{
        String insertCode = "UPDATE students SET email = ? WHERE student_id =?";
        try{
            PreparedStatement statement = connection.prepareStatement(insertCode);
            statement.setString(1,new_email);
            statement.setInt(2,student_id);

            statement.executeUpdate();
            System.out.println("Email updated successfully.");


        }
        catch (Exception e){
            throw e;
        }
    }

    public void deleteStudent(int student_id) throws  Exception{
        String insertCode = "DELETE  FROM students WHERE student_id =?";
        try{
            PreparedStatement statement = connection.prepareStatement(insertCode);
            statement.setInt(1,student_id);

            statement.executeUpdate();
            System.out.println("Student removed successfully.");


        }
        catch (Exception e){
            throw e;
        }
    }

    public static void main (String[] args){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String user = input.nextLine();
        System.out.print("Enter your password: ");
        String password = input.nextLine();
        System.out.print("Enter the name of your database: ");
        String url = "jdbc:postgresql://localhost:5432/" + input.nextLine();

        try{
            int choice;
            DatabaseAccessor access = new DatabaseAccessor (url, user, password);
            do{
                System.out.println();
                System.out.println("Enter the number corresponding to your choice:");
                System.out.println("1- View all students");
                System.out.println("2- Add a new student");
                System.out.println("3- Update a student's email");
                System.out.println("4- Remove a student");
                System.out.println("0-exit");
                System.out.print("Selection: ");
                choice = input.nextInt();


                switch(choice){
                    case 0:
                        System.out.println("Exiting program");
                        break;
                    case 1:
                        access.getAllStudents();
                        break;
                    case 2:
                        input.nextLine();
                        System.out.print("Enter the student's first name: ");
                        String first_name = input.nextLine();
                        System.out.print("Enter the student's last name: ");
                        String last_name = input.nextLine();
                        System.out.print("Enter the student's email: ");
                        String email = input.nextLine();
                        System.out.print("Enter the student's enrolment date: ");
                        String enrollment_date = input.nextLine();
                        access.addStudent(first_name, last_name, email, enrollment_date);
                        break;
                    case 3:
                        System.out.print("Enter the id for the student you would like to update: ");
                        int id = input.nextInt();
                        input.nextLine();
                        System.out.print("Enter their new email: ");
                        String new_email = input.nextLine();
                        access.updateStudentEmail(id, new_email);
                        break;

                    case 4:
                        System.out.print("Enter the id for the student you would like to remove: ");
                        int stu_id = input.nextInt();
                        access.deleteStudent(stu_id);
                        break;

                }
            }while (choice !=0);

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

}
