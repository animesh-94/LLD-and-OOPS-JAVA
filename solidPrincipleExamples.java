// SOLID Payroll System - Single Code File

// -------- Pay Calculation Abstraction (LSP) --------
interface PayableEmployee {
    double calculatePay();
    String getEmployeeType();
}

// -------- Report Abstraction (ISP) --------
interface ReportService {
    void generateReport(PayableEmployee employee);
}

// -------- Concrete Employee Pay Implementations (SRP + LSP) --------
class HourlyEmployeePay implements PayableEmployee {
    private int hoursWorked;
    private double hourlyRate;

    public HourlyEmployeePay(int hoursWorked, double hourlyRate) {
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculatePay() {
        return hoursWorked * hourlyRate;
    }

    @Override
    public String getEmployeeType() {
        return "Hourly Employee";
    }
}

class SalariedEmployeePay implements PayableEmployee {
    private double monthlySalary;

    public SalariedEmployeePay(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculatePay() {
        return monthlySalary;
    }

    @Override
    public String getEmployeeType() {
        return "Salaried Employee";
    }
}

class ManagerPay implements PayableEmployee {
    private double baseSalary;
    private double bonus;

    public ManagerPay(double baseSalary, double bonus) {
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }

    @Override
    public double calculatePay() {
        return baseSalary + bonus;
    }

    @Override
    public String getEmployeeType() {
        return "Manager";
    }
}

// -------- Payroll Processor (Proves LSP) --------
class PayrollProcessor {
    public void processPayroll(PayableEmployee employee) {
        System.out.println(
            employee.getEmployeeType() +
            " payroll processed. Pay = " +
            employee.calculatePay()
        );
    }
}

// -------- Report Implementations (OCP) --------
class PayStubReport implements ReportService {
    @Override
    public void generateReport(PayableEmployee employee) {
        System.out.println(
            "Pay Stub Report for " +
            employee.getEmployeeType() +
            " : Pay = " +
            employee.calculatePay()
        );
    }
}

class TaxLiabilityReport implements ReportService {
    @Override
    public void generateReport(PayableEmployee employee) {
        double tax = employee.calculatePay() * 0.20;
        System.out.println(
            "Tax Liability Report for " +
            employee.getEmployeeType() +
            " : Tax = " +
            tax
        );
    }
}

// -------- Main Class --------
public class Main {
    public static void main(String[] args) {

        PayableEmployee hourlyEmployee = new HourlyEmployeePay(40, 25);
        PayableEmployee salariedEmployee = new SalariedEmployeePay(5000);
        PayableEmployee manager = new ManagerPay(7000, 2000);

        PayrollProcessor payrollProcessor = new PayrollProcessor();

        payrollProcessor.processPayroll(hourlyEmployee);
        payrollProcessor.processPayroll(salariedEmployee);
        payrollProcessor.processPayroll(manager);

        ReportService payStubReport = new PayStubReport();
        ReportService taxReport = new TaxLiabilityReport();

        payStubReport.generateReport(hourlyEmployee);
        taxReport.generateReport(manager);
    }
}
