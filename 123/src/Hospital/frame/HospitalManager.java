package Hospital.frame;
import Hospital.Class.Appointment;
import Hospital.Class.Department;
import Hospital.Class.Doctor;
import Hospital.Class.Schedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class HospitalManager {
    private ArrayList<Department> allDepartments = new ArrayList<>();
    private ArrayList<Appointment> allAppointments = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public void start(){
        while(true){
            System.out.println("====欢迎进入人民医院信息管理系统==== ");
            System.out.println("1.科室管理-添加科室 ");
            System.out.println("2.科室管理-删除科室 ");
            System.out.println("3.科室管理-修改科室 ");
            System.out.println("4.医生管理-录入医生 ");
            System.out.println("5.医生管理-医生坐诊设置 ");
            System.out.println("6.医生管理-展示医生坐诊情况 ");
            System.out.println("7.医生管理-挂号预约 ");
            System.out.println("8.搜索医生预约详情");
            System.out.println("请输入命令！");
            Scanner sc = new Scanner(System.in);

            switch(sc.next()){
                case "1":
                    adadDepartment();
                    break;

                case "3":
                    addDoctor();
                    break;

                case "5":
                    setDoctorJob();
                    break;

                case "6":
                    break;
            }
        }
    }

    private Department getDepartmentByUser(){
        if(allDepartments.isEmpty()){
            return null;
        }
        while(true){
            System.out.println("请选择科室： ");
            for(int i = 0; i < allDepartments.size(); i++){
                Department department = allDepartments.get(i);
                System.out.println((i + 1) + "." + department.getName());
            }
            System.out.println("请选择");
            int command = sc.nextInt();
            if(command < 1 || command > allDepartments.size()){
                System.out.println("选择有误，请重新输入");
                continue;
            }
            Department department = allDepartments.get(command - 1);
            return department;
        }
    }

    /*private Doctor getDoctorByUser(Department department, String name){
        if(department == null)return null;
        for(int i = 0 ; i < department.getDoctors().size(); i++){
            Doctor doctor = department.getDoctors().get(i);
            if(doctor.getName().equals(name))return doctor;
        }
        return null;
    }*/

    private void adadDepartment(){
        System.out.println("======添加科室======");
        OUT:
        while(true){
            System.out.println("请输入科室名称:  ");
            String name  = sc.next();
            if(name.equals("exit"))break;
            for(int i = 0; i < allDepartments.size(); i++){
                Department department = allDepartments.get(i);
                if(department.getName().equals(name))continue OUT;
            }
            Department department = new Department();
            department.setName(name);
            allDepartments.add(department);
            break;
        }
    }

    private void addDoctor(){
        System.out.println("======添加医生======");
        if(allDepartments.size() == 0){
            System.out.println("您当前没有科室，无法录入");
            return;
        }
        Doctor doctor = new Doctor();
        while(true){

            System.out.println("请选择科室： ");
            for(int i = 0; i < allDepartments.size(); i++){
                Department department = allDepartments.get(i);
                System.out.println((i + 1) + "." + department.getName());
            }
            System.out.println("请选择");
            int command = sc.nextInt();
            if(command < 1 || command > allDepartments.size()){
                System.out.println("选择有误，请重新输入");
                continue;
            }
            Department department = allDepartments.get(command - 1);
            doctor.setDepartmentName(department.getName());
            doctor.setDoctorID(UUID.randomUUID().toString());

            System.out.println("请输入医生的姓名");
            String name = sc.next();
            doctor.setName(name);

            System.out.println("请输入医生的性别");
            String sex = sc.next();
            doctor.setGender(sex);

            System.out.println("请输入医生的年龄");
            int age = sc.nextInt();
            doctor.setAge(age);

            System.out.println("请输入医生的特长");
            String specialty = sc.next();
            doctor.setSpecialty(specialty);

            System.out.println("请输入以上的入职日期(yyyy-mm-dd)");
            String joinDateString = sc.next();
            LocalDate joindate = LocalDate.parse(joinDateString);
            doctor.setJoinDate(joindate);

            department.getDoctors().add(doctor);
            break;
        }
    }

    private void setDoctorJob(){
        System.out.println("请输入科室");
        Department department = getDepartmentByUser();
        ArrayList<Doctor> doctors = department.getDoctors();
        if(doctors.isEmpty()){
            System.out.println("当前科室没有医生");
            return;
        }
        while(true){
            System.out.println("当前科室的医生如下");
            for(int i = 0; i < doctors.size(); i++){
                Doctor doctor = doctors.get(i);
                System.out.println((i + 1) + "." + doctor.getName());
            }
            System.out.println("请输入：");

            int command = sc.nextInt();
            if(command < 1 || command > doctors.size()){
                System.out.println("输入有误，请重新输入");
                continue;
            }
            Doctor doctor = doctors.get(command - 1);
            ArrayList<Schedule> schedules = doctor.getSchedules();
            updateSchedules(schedules);



        }

    }
    private void updateSchedules(ArrayList<Schedule> schedules){
        if(schedules.size() == 0){
            for(int i = 0; i <= 6; i ++ ){
                Schedule schedule = new Schedule();
                LocalDate now = LocalDate.now();
                schedule.setToday(now.plusDays(i));
                schedules.add(schedule);
            }
            return;
        }

        for(int i = 0; i < schedules.size(); i++){
            Schedule schedule = schedules.get(i);
            LocalDate now = LocalDate.now();
            LocalDate current = schedule.getToday();
            if(current.equals(now)){
                break;
            }
            if(current.isBefore(now)){
                schedules.remove(schedule);
                i -- ;
            }
        }

        LocalDate last = schedules.get(schedules.size() - 1).getToday();
        for(int i = 0; i < 7 - schedules.size(); i ++ ){
            Schedule schedule = new Schedule();
            schedule.setToday(last.plusDays(i + 1));
            schedules.add(schedule);
        }
    }

}
