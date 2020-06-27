/**
 *It is an employee database program.This is thanghavel
 * This program tends to insert,fetch,remove and lists the employee details.
 */
import java.sql.*;
import java.util.*;

public class Employee {
    public static void main(String[] args) {

        /*
        * Variables used for JDBC connection
        */

        String url="jdbc:mysql://localhost:3306/today";
        String username="root";
        String password="3103";

        /*
        * Infinite loop for operations
        */

        retry: while(true){      /* branch label for invalid input */

            /* Initializing variables */

            int arrayListIndex=0,i,j,flag=0;
            String concatValues="",id,name,age,deptName,sqlInsert,sqlFetch,sqlRemove,sqlList,fetch,remove,answer;
            /* Initialising objects */

            ArrayList<String>[] list=new ArrayList[100];
            ArrayList<String> item=new ArrayList<String>();
            HashMap<String,String> hm=new HashMap<String,String>();
            Scanner sc = new Scanner(System.in);

            System.out.println("Enter the implemetation method:\n 1.Hashmap\n 2.Arraylist \n 3.JDBC");
            String choice = sc.nextLine();

            /* Checking choices*/

            if (!choice.equals("1") & !choice.equals("2") & !choice.equals("3")) {
                System.out.println("No such entry!");
                continue retry;
            } else {                                        // If choice is valid

                /*
                * Infinite loop until other than 'y' has encountered
                 */

                while(true){
                    System.out.println("Enter any option:\n 1.Adding\n 2.Fetching \n 3.Removing \n 4.Listing");
                    String option=sc.nextLine();

                    /*
                    * Switch case for operations
                     */

                    switch(option){

                        /*
                        * Adding module
                         */

                        case "1":
                            System.out.println("Adding");
                            EmployeeDetails emp = new EmployeeDetails();
                            System.out.println("Enter id:");
                            emp.id = sc.nextInt();
                            item.add(Integer.toString(emp.id));
                            sc.nextLine();
                            System.out.println("Enter name:");
                            emp.name = sc.nextLine();
                            item.add(emp.name);
                            System.out.println("Enter age:");
                            emp.age = sc.nextInt();
                            item.add(Integer.toString(emp.age));
                            sc.nextLine();
                            System.out.println("Enter dept name:");
                            emp.deptName = sc.nextLine();
                            item.add(emp.deptName);

                            /*
                            * Switch case for choice checking
                             */

                            switch(choice){

                                /*
                                * Inserting with hashmap
                                 */

                                case "1":
                                    for ( i = 0; i < item.size(); i++) {
                                        concatValues = concatValues + item.get(i) + " ";
                                    }
                                    if (hm.containsKey(item.get(0))){
                                        System.out.println("Key already present!!! Cannot insert");
                                    } else{
                                        hm.put(item.get(0), concatValues);
                                    }
                                    concatValues = "";
                                    break;

                                    /*
                                    * Inserting with arraylist
                                     */

                                case "2":
                                    list[arrayListIndex] = new ArrayList<String>();
                                    for (i = 0; i < item.size(); i++) {
                                        list[arrayListIndex].add(item.get(i));
                                    }
                                    arrayListIndex++;
                                    break;

                                    /*
                                    * Inserting with database
                                     */

                                case "3":
                                    try{
                                        Connection connection = DriverManager.getConnection(url,username,password);
                                        System.out.println("Connected");
                                        sqlInsert="INSERT INTO EMPLOYEE VALUES (?,?,?,?)";
                                        PreparedStatement statement=connection.prepareStatement(sqlInsert);
                                        System.out.println("Enter id");
                                        id=sc.nextLine();
                                        statement.setString(1 , id);
                                        System.out.println("Enter name");
                                        name=sc.nextLine();
                                        statement.setString(2,name);
                                        System.out.println("Enter age");
                                        age=sc.nextLine();
                                        statement.setString(3,age);
                                        System.out.println("Enter dept name");
                                        deptName=sc.nextLine();
                                        statement.setString(4,deptName);
                                        int rows=statement.executeUpdate();
                                        if (rows >0)
                                        {
                                            System.out.println("row inserted");
                                        }
                                        statement.close();
                                        connection.close();
                                    } catch(SQLException e){
                                        System.out.println(e);
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                            break;

                        /*
                         * Fetching module
                         */

                        case "2":
                            System.out.println("Fetching");
                            System.out.println("Enter the ID to fetch:");
                            fetch=sc.nextLine();

                            /*
                            * Choice checking switch case
                             */

                            switch(choice){

                                /*
                                * Fetching with hashmap
                                */

                                case "1":
                                    for (String k : hm.keySet()) {
                                        if (fetch.equals(k)) {
                                            System.out.println(hm.get(k));
                                            flag=1;
                                            continue;
                                        }
                                        if (flag==0){
                                            System.out.println("Requested ID is not present!");
                                        }
                                    }
                                    break;

                                 /*
                                 * Fetching with arraylist
                                 */

                                case "2":
                                    for (i=0;i<arrayListIndex;i++) {
                                        if (list[i].get(0).equals(fetch)) {
                                            System.out.println(list[i].get(1)+" "+list[i].get(2)+" "+list[i].get(3));
                                            flag=1;
                                        }
                                    }
                                    if (flag==0){
                                        System.out.println("No such record found!");
                                    }
                                    break;

                                 /*
                                 * Fetching with database
                                  */

                                case "3":
                                    try {
                                        Connection connection = DriverManager.getConnection(url, username, password);
                                        Statement statement = connection.createStatement();
                                        sqlFetch = "select * from employee where id='" + fetch + "'";
                                        ResultSet result = statement.executeQuery(sqlFetch);
                                        while (result.next()) {
                                            id = result.getString(1);
                                            name = result.getString(2);
                                            age = result.getString(3);
                                            deptName = result.getString(4);
                                            System.out.println(id + " " + name + "" + age + "" + deptName);
                                        }
                                    } catch(SQLException e) {
                                        System.out.println(e);
                                    }
                                    break;
                            }
                            break;
                        case "3":

                            /*
                            * Removing module
                             */

                            System.out.println("Removing");
                            System.out.println("Enter the id to remove the data:");
                            remove=sc.nextLine();
                            switch(choice){
                                case "1":
                                    for (String k:hm.keySet()) {
                                        if (k.equals(remove)) {
                                            hm.remove(remove);
                                            System.out.println("Removed Successfully!");
                                            flag=1;
                                        }
                                    }
                                    if (flag==0) {
                                        System.out.println("Requested ID is not present!");
                                    }
                                    break;
                                case "2":
                                    for(i=0;i<arrayListIndex;i++) {
                                        if (list[i].get(0).equals(remove)) {
                                            list[i].clear();
                                            flag=1;
                                            break;
                                        }
                                    }
                                    if(flag==0) {
                                        System.out.println("Requested ID is not present!");
                                    }
                                    break;
                                case "3":
                                    try{
                                        Connection connection = DriverManager.getConnection(url,username,password);
                                        Statement statementd=connection.createStatement();
                                        sqlRemove="delete from employee where id='"+remove+"'";
                                        statementd.executeUpdate(sqlRemove);
                                        statementd.close();
                                    }
                                    catch(SQLException e){
                                        System.out.println(e);
                                    }
                                    break;
                            }
                            break;
                        case "4":

                            /*
                            * Listing module
                             */

                            switch(choice){
                                case "1":
                                    for (String k : hm.keySet()) {
                                        if (k.length()>0)
                                            System.out.println(hm.get(k));
                                    }
                                    break;
                                case "2":
                                    for (i = 0; i <arrayListIndex; i++) {
                                        for (j = 0; j < list[i].size(); j++) {
                                            System.out.println(list[i].get(j) + " ");
                                        }
                                        System.out.println(list[i].size());
                                    }
                                    break;
                                case "3":
                                    try {
                                        Connection connection = DriverManager.getConnection(url,username,password);
                                        sqlList="SELECT * FROM EMPLOYEE";
                                        Statement statement1=connection.createStatement();
                                        ResultSet result=statement1.executeQuery(sqlList);
                                        while(result.next()) {
                                            id=result.getString(1);
                                            name=result.getString(2);
                                            age=result.getString(3);
                                            deptName=result.getString(4);
                                            System.out.println(id+" "+name+" "+age+" "+deptName);
                                        }
                                        statement1.close();
                                        connection.close();
                                    }
                                    catch(SQLException e) {
                                        System.out.println("Cannot connect");
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                            break;
                        default:{
                            System.out.println("Invalid option");
                            break;
                        }
                        }
                    System.out.println("Wish to continue?");
                    answer=sc.nextLine();
                    if (answer.equals("y")) {
                        continue;
                    } else {
                        System.out.println("Ending program!!!");
                        break;
                    }
                    }

                }

            }
        }
    }

