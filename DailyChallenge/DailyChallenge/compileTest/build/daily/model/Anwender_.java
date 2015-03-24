package daily.model;

import daily.model.Kategorie;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-03-24T12:03:31")
@StaticMetamodel(Anwender.class)
public class Anwender_ { 

    public static volatile SingularAttribute<Anwender, Integer> score;
    public static volatile ListAttribute<Anwender, Kategorie> kategorie;
    public static volatile SingularAttribute<Anwender, Long> id;
    public static volatile SingularAttribute<Anwender, String> email;
    public static volatile SingularAttribute<Anwender, String> username;
    public static volatile SingularAttribute<Anwender, String> userpassword;

}