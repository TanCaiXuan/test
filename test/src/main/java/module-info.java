module com.caixuan.test {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.caixuan.test to javafx.fxml;
    exports com.caixuan.test;
}