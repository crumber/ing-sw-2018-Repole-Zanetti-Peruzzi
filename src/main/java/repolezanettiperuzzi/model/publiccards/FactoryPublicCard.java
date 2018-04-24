package repolezanettiperuzzi.model.publiccards;

public class FactoryPublicCard {
    public static PublicCard getPublicCard(String criteria)
    {
        if ( criteria.equals("pc01") )
            return (PublicCard) new ColorVariety();
        else if ( criteria.equals("pc02") )
            return (PublicCard) new ColorDiagonals();
        else if ( criteria.equals("pc03") )
            return (PublicCard) new ColumnColorVariety();
        else if ( criteria.equals("pc04") )
            return (PublicCard) new ColumnShadeVariety();
        else if ( criteria.equals("pc05") )
            return (PublicCard) new RowColorVariety();
        else if ( criteria.equals("pc06") )
            return (PublicCard) new RowShadeVariety();
        else if ( criteria.equals("pc07") )
            return (PublicCard) new ShadeVariety();
        else if ( criteria.equals("pc08") )
            return (PublicCard) new MediumShades();
        else if ( criteria.equals("pc09") )
            return (PublicCard) new LightShades();
        else if ( criteria.equals("pc10") )
            return (PublicCard) new DeepShades();

        return null;
    }
}
