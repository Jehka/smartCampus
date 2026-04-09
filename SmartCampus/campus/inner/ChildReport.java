package campus.inner;

public class ChildReport extends AdminPanel.Report {
    private String semester;
    public ChildReport(String title, String semester) {
        super(title);
        this.semester = semester;
    }
    @Override
    public void printReport() {
        System.out.println("[ChildReport] Semester: " + semester);
        super.printReport();
    }
}
