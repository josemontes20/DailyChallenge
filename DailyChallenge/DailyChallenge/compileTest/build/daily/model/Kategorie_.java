package daily.model;

import daily.model.Anwender;
import daily.model.Challenge;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-03-24T12:03:31")
@StaticMetamodel(Kategorie.class)
public class Kategorie_ { 

    public static volatile ListAttribute<Kategorie, Challenge> challenges;
    public static volatile SingularAttribute<Kategorie, String> name;
    public static volatile SingularAttribute<Kategorie, Long> id;
    public static volatile ListAttribute<Kategorie, Anwender> anwender;

}