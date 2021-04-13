package main;

import main.HibernateUtil;
import main.model.Director;
import main.model.Movie;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.criteria.CriteriaBuilderImpl;
import org.hibernate.query.Query;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        query1(session);
        query2(session);
        query3(session);
        query4(session);
        query5(session);
        query6(session);


        session.close();
        System.out.println("OK");
        System.exit(0);
    }
    public static void query1(Session session) {
        Query query1 = session.createQuery("select new list (movie.name, movie.year, movie.directors.size) from Movie as movie");
        System.out.println(query1.getResultList());
    }
    public static void query2(Session session) {
        Query query2 = session.createQuery("select directors from Movie as movie where movie.year > ?1 and movie.year < ?2");
        query2.setParameter(1, 1979).setParameter(2, 1990);
        System.out.println(query2.getResultList());

    }
    public static void query3(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> movie = cq.from(Movie.class);
        cq.where(cb.and(
                cb.greaterThanOrEqualTo(movie.get("year"), 1995),
                cb.lessThanOrEqualTo(movie.get("year"), 2000)
        ));
        cq.select(movie);
        Query q3 = session.createQuery(cq);
        System.out.println(q3.getResultList());

    }
    public static void query4(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<Movie> root = cq.from(Movie.class);
        Join<Movie, Director> d = root.join("directors");

        cq.where((cb.equal(d.get("firstName"), "David")));
        cq.select(cb.avg(root.get("rank")));

        Query q4 = session.createQuery(cq);
        System.out.println(q4.getSingleResult());
    }
    public static void query5(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<Movie> movieroot = cq.from(Movie.class);

        Join<Movie, Director> joinM = movieroot.join("directors");

        cq.where(cb.and(
                cb.greaterThanOrEqualTo(movieroot.get("year"), 2000),
                cb.isNotNull(movieroot.get("rank"))
        ));

        cq.select(joinM.get("lastName"));

        Query q5 = session.createQuery(cq);
        System.out.println(q5.getResultList());
    }
    public static void query6(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> movieroot = cq.from(Movie.class);

        Subquery<Double> subquery = cq.subquery(Double.class);
        Root frommovie = subquery.from(Movie.class);

        subquery.select(cb.max(frommovie.get("rank")));
        cq.where(cb.equal(movieroot.get("rank"), subquery));
        cq.select(movieroot);

        Query q6 = session.createQuery(cq);
        System.out.println(q6.getResultList());
    }

}


//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
//        Root<Movie> movieroot = cq.from(Movie.class);
//
//        cq.select(movieroot);
//        cq.orderBy(cb.desc(movieroot.get("rank")));
//        Query q6 = session.createQuery(cq);
//        System.out.println(q6.getResultList());