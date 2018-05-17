package repolezanettiperuzzi.model.publiccards;

public class FactoryPublicCard {

    public static PublicCard getPublicCard(String criteria)
    {
        if ( criteria.equals("pc1") )
            return new ColorVariety();
        else if ( criteria.equals("pc2") )
            return new ColorDiagonals();
        else if ( criteria.equals("pc3") )
            return new ColumnColorVariety();
        else if ( criteria.equals("pc4") )
            return new ColumnShadeVariety();
        else if ( criteria.equals("pc5") )
            return new RowColorVariety();
        else if ( criteria.equals("pc6") )
            return new RowShadeVariety();
        else if ( criteria.equals("pc7") )
            return new ShadeVariety();
        else if ( criteria.equals("pc8") )
            return new MediumShades();
        else if ( criteria.equals("pc9") )
            return new LightShades();
        else if ( criteria.equals("pc10") )
            return new DeepShades();

        return null;
    }
}
