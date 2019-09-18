package com.myTraining._Learning_Hibernate;

import java.io.Serializable;

public class CompositeKeyExample {

	public static void main(String arv[])
	{
		SessionFactory sessionFactory = Configuration.configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		session.beginTransation();
		
		Integer levelStation = 10;
		Integer confPathID = 20;
		Time time = (Time)session.get(Time.class, new TimePK(levelStation, confPathID ));
		
		session.getTransation().commit();
		session.close();
	}
}

// With an IdClass

class TimePK implements Serializable {
    protected Integer levelStation;
    protected Integer confPathID;

    public TimePK() {}

    public TimePK(Integer levelStation, Integer confPathID) {
        this.levelStation = levelStation;
        this.confPathID = confPathID;
    }
    // equals, hashCode
}

@Entity
@IdClass(TimePK.class)
class Time implements Serializable {
    @Id
    private Integer levelStation;
    @Id
    private Integer confPathID;

    private String src;
    private String dst;
    private Integer distance;
    private Integer price;

    // getters, setters
}

//========================================================

// With EmbeddedId

@Embeddable
 class TimePK implements Serializable {
    protected Integer levelStation;
    protected Integer confPathID;

    public TimePK() {}

    public TimePK(Integer levelStation, Integer confPathID) {
        this.levelStation = levelStation;
        this.confPathID = confPathID;
    }
    // equals, hashCode
}


@Entity
class Time implements Serializable {
    
	@EmbeddedId
    private TimePK timePK;

    private String src;
    private String dst;
    private Integer distance;
    private Integer price;

    //...
}