package daily.model;

import daily.model.Kategorie;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-03-24T12:03:31")
@StaticMetamodel(Challenge.class)
public class Challenge_ { 

    public static volatile SingularAttribute<Challenge, String> aktivAmDatum;
    public static volatile SingularAttribute<Challenge, Kategorie> kategorie;
    public static volatile SingularAttribute<Challenge, Long> id;
    public static volatile SingularAttribute<Challenge, String> beschreibung;

}