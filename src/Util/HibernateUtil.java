/*
 * This class not only produces the global org.hibernate.SessionFactory reference in its static initializer;
 * it also hides the fact that it uses a static singleton
*/

package Util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static final SessionFactory sessionFactoryMypay;
    private static final SessionFactory sessionFactoryMypayCenter;

    static {
    	// 註冊服務
    	final StandardServiceRegistry registryMypayCenter = new StandardServiceRegistryBuilder()
                .configure("MyPayCenterCP30_hibernate.cfg.xml").build();
        final StandardServiceRegistry registryMyPay = new StandardServiceRegistryBuilder()
                .configure("MyPayCP30_hibernate.cfg.xml").build();
        try {
            // 創建SessionFactory
            sessionFactoryMypayCenter = new MetadataSources(registryMypayCenter).buildMetadata().buildSessionFactory();
            sessionFactoryMypay = new MetadataSources(registryMyPay).buildMetadata().buildSessionFactory();
        } catch (Throwable ex) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registryMypayCenter);
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getMypayCenterSessionFactory() {
        return sessionFactoryMypayCenter;
    }
    public static SessionFactory getMypaySessionFactory() {
        return sessionFactoryMypay;
    }
}