package main;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static SessionFactory sessionFactory;// ��������� � ������ � �������� (������� ������)

    static {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
	// ��������� ������� �������� (http://docs.jboss.org/hibernate/orm/5.3/integrationsGuide/html_single/)
                .configure() // ��������� �� hibernate.cfg.xml
                .build();
        try {
            // MetadataSources - ��� ������ � ����������� �������� ��������
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy( registry );
            throw new RuntimeException("There is a error building session factory");
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}