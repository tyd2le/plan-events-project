import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Main {
    public static ArrayList<String> logins = new ArrayList<>();

    public static HashMap<String, Boolean> HM_login = new HashMap<>();

    public static HashMap<String, Boolean> HM_email = new HashMap<>();

    public static HashMap<String, String> HM_password = new HashMap<>();

    public static String current_login;

    public static ArrayList<String> current_plan = new ArrayList<>();

    public static void data_base(){
        try {
            File file = new File("data_base.txt");

            if (file.createNewFile()) {
                System.out.println("Файл создан");
            }
            else {
                System.out.println("Файл уже существует");
            }
        }
        catch (IOException e) {
            System.out.println("Ошибка при создании файла");
        }

        try {
            FileReader fileReader = new FileReader("data_base.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.isEmpty()){
                    continue;
                }

                String login = "";
                String email = "";
                String password = "";

                for (int i=0, k=0, c=0; i<line.length(); ++i){
                    if (line.charAt(i) == ' ' || i+1 == line.length()){
                        ++c;
                        if (c == 1){
                            login = line.substring(k, i);
                        }
                        else if (c == 2){
                            email = line.substring(k, i);
                        }
                        else{
                            password = line.substring(k, i+1);
                        }
                        k = i+1;
                    }
                }
                logins.add(login + " " + email + " " + password);

                HM_login.put(login, true);
                HM_email.put(email, true);
                HM_password.put(login, password);
            }

            bufferedReader.close();
        }
        catch (IOException e) {
            System.out.println("Ошибка при чтении файла");
        }
    }

    public static void sign_up_or_sign_in(){
        Scanner scan = new Scanner(System.in);

        System.out.println("1 - sign up");
        System.out.println("2 - sign in");

        while(true){
            int n = scan.nextInt();

            if (n == 1){
                sign_up();
                sign_in();
                break;
            }
            else if (n == 2){
                sign_in();
                break;
            }
            else {
                System.out.println("unexpected input");
            }
        }
    }

    public static void sign_up() {
        Scanner scan = new Scanner(System.in);

        String login;
        String email;
        String password = "";

        while (true){
            System.out.print("login: ");
            login = scan.nextLine();

            if(HM_login.get(login) == null){
                HM_login.put(login, true);
                break;
            }
            else{
                System.out.println("this login is exist");
            }
        }

        while (true){
            System.out.print("email: ");
            email = scan.nextLine();

            if(HM_email.get(email) == null){
                HM_email.put(email, true);
                break;
            }
            else{
                System.out.println("this email is exist");
            }
        }

        boolean again = true;

        while (again) {
            System.out.println("---------------------------------------------------");
            System.out.println("length of password must be longer than/or 4 symbols");
            System.out.println("in password must include a letter");
            System.out.println("space is not valuable");
            System.out.println("---------------------------------------------------");

            System.out.print("password: ");

            password = scan.nextLine();

            if (password.length() >= 4){
                for (int i=0; i<password.length(); ++i){
                    if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z'){
                        HM_password.put(login, password);
                        again = false;
                        break;
                    }
                    if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z'){
                        HM_password.put(login, password);
                        again = false;
                        break;
                    }
                }
            }
            if (again){
                System.out.println("this password is not permitted");
            }
        }

        while (true) {
            System.out.print("repeat password: ");

            if (scan.nextLine().equals(password)){
                break;
            }
            else{
                System.out.println("try again");
            }
        }

        save_login(login, email, password);
    }

    public static void save_login(String login, String email, String password){
        try {
            File file = new File(login + ".txt");
            if (file.createNewFile()){
                System.out.println("Файл создан");
            }
            else{
                System.out.println("Файл уже существует");
            }
        }
        catch (IOException e){
            System.out.println("Ошибка при создании файла");
        }

        logins.add(login + " " + email + " " + password);

        try {
            FileWriter fileWriter = new FileWriter("data_base.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (String x : logins){
                bufferedWriter.write(x);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        }
        catch (IOException e) {
            System.out.println("Ошибка при записи в файл");
        }
    }

    public static void sign_in(){
        Scanner scan = new Scanner(System.in);

        String login;
        String password;

        while(true){
            System.out.print("login: ");
            login = scan.nextLine();

            if(HM_login.get(login) != null){
                break;
            }
            else{
                System.out.println("this login is not exist");
            }
        }
        current_login = login + ".txt";

        while(true) {
            System.out.print("password: ");
            password = scan.nextLine();

            if (password.equals(HM_password.get(login))){
                break;
            }
            else{
                System.out.println("this password is incorrect");
            }
        }
    }

    public static void information_from_plan(){
        try {
            FileReader fileReader = new FileReader(current_login);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isEmpty()){
                    continue;
                }
               current_plan.add(line);
            }

            bufferedReader.close();
        }
        catch (IOException e) {
            System.out.println("Ошибка при чтении файла");
        }
    }

    public static void plan(){
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("-------------------");
            System.out.println("1 - create the plan");
            System.out.println("2 - read the plan");
            System.out.println("3 - delete the plan");
            System.out.println("4 - exit");
            System.out.println("-------------------");

            int n = scan.nextInt();

            if (n == 1){
                scan.nextLine();
                String s = scan.nextLine();

                current_plan.add(s);

                try {
                    FileWriter fileWriter = new FileWriter(current_login);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                    for (String x : current_plan){
                        bufferedWriter.write(x);
                        bufferedWriter.newLine();
                    }

                    bufferedWriter.close();
                }
                catch (IOException e) {
                    System.out.println("Ошибка при записи в файл");
                }

                System.out.println("press any key to continue");
                scan.nextLine();
                scan.nextLine();
            }
            else if (n == 2) {
                try {
                    FileReader fileReader = new FileReader(current_login);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    String line;
                    int c = 0;

                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.isEmpty()){
                            System.out.println("plan is empty");
                            break;
                        }
                        ++c;
                        System.out.println(c + ". " + line);
                    }

                    bufferedReader.close();
                }
                catch (IOException e) {
                    System.out.println("Ошибка при чтении файла");
                }

                System.out.println("press any key to continue");
                scan.nextLine();
                scan.nextLine();
            }
            else if (n == 3) {
                while (true) {
                    if (current_plan.isEmpty()){
                        System.out.println("plan is empty");
                        break;
                    }
                    System.out.println("what number of plan do you want to delete?");

                    int n2 = scan.nextInt();
                    --n2;

                    if (n2 >= current_plan.size()){
                        System.out.println("unexpected input");
                        continue;
                    }

                    current_plan.remove(n2);

                    try {
                        FileWriter fileWriter = new FileWriter(current_login);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        for (String x : current_plan) {
                            bufferedWriter.write(x);
                            bufferedWriter.newLine();
                        }

                        bufferedWriter.close();
                    }
                    catch (IOException e) {
                        System.out.println("Ошибка при записи в файл");
                    }

                    break;
                }
                System.out.println("press any key to continue");
                scan.nextLine();
                scan.nextLine();
            }
            else if (n == 4){
                boolean the_end = true;

                while (true) {
                    System.out.println("you want to exit?");
                    System.out.println("1 - yes");
                    System.out.println("2 - no");

                    int n2 = scan.nextInt();

                    if (n2 == 1) {
                        System.out.println("see you again");
                        the_end = false;
                        break;
                    }
                    else if (n2 == 2) {
                        System.out.println("welcome again");

                        System.out.println("press any key to continue");
                        scan.nextLine();
                        scan.nextLine();

                        break;
                    }
                    else {
                        System.out.println("unexpected input");
                    }
                }
                if (the_end == false){
                    break;
                }
            }
            else {
                System.out.println("unexpected input");
            }
        }
    }

    public static void main(String[] args){
        data_base();

        sign_up_or_sign_in();

        information_from_plan();

        plan();
    }
}
