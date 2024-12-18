module org.example.jfxhibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires org.hibernate.orm.core;

    requires java.naming;
    requires jakarta.persistence;
    requires java.desktop;
    requires javafx.media;

    requires net.sf.jasperreports.core;




    opens org.example.jfxhibernate.models;


    exports org.example.jfxhibernate;
    opens org.example.jfxhibernate;
    exports org.example.jfxhibernate.controller;
    opens org.example.jfxhibernate.controller;
    exports org.example.jfxhibernate.controller.admistrador;
    opens org.example.jfxhibernate.controller.admistrador;
    exports org.example.jfxhibernate.controller.usuario;
    opens org.example.jfxhibernate.controller.usuario;
}