import java.util.ArrayList;

class Department {
    private String deptName;
    private String deptCode;

    private ArrayList<Professors> professors;

    Department(String deptName, String deptCode){
        this.deptName = deptName;
        this.deptCode = deptCode;
        this.professors = new ArrayList<>();
    }

    public void addProfessor(Professors p){
        professors.add(p);
    }

    public void displayDepartmentDetails(){
        System.out.println("Department: " + deptName + " (" + deptCode + ")");
        System.out.println("Professors:");

        if(professors.isEmpty()){
            System.out.println("No Professors enrolled");
        } else {
            for(Professors p : professors){
                System.out.println("Name: " + p.getName() + 
                                   ", Subject: " + p.getSubject());
            }
        }
    }
}

class Professors {
    private String name;
    private String professorId;
    private String subject;

    Professors(String name, String professorId, String subject){
        this.name = name;
        this.professorId = professorId;
        this.subject = subject;
    }

    public String getName(){
        return name;
    }

    public String getProfessorId(){
        return professorId;
    }

    public String getSubject(){
        return subject;
    }

    void setName(String name){
        this.name = name;
    }

    void setProfessorId(String professorId){
        this.professorId = professorId;
    }

    void setSubject(String subject){
        this.subject = subject;
    }
}

public class Main {
    public static void main(String[] args) {

        // Corrected constructor parameter order
        Professors p1 = new Professors("Dr. Sharma", "P101", "Data Structures");
        Professors p2 = new Professors("Dr. Kulkarni", "P102", "Operating Systems");

        Department csDept = new Department("Computer Science", "CS101");

        csDept.addProfessor(p1);
        csDept.addProfessor(p2);

        csDept.displayDepartmentDetails();
    }
}
