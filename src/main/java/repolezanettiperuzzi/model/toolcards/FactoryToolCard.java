package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.publiccards.ColorVariety;

public class FactoryToolCard {
    public static ToolCard getPublicCard(String criteria)
    {
        if ( criteria.equals("tc1") )
            return new CopperFoilBurnisher();
        else if ( criteria.equals("tc2") )
            return new CorkbackedStraightedge();
        else if ( criteria.equals("tc3") )
            return new EglomiseBrush();
        else if ( criteria.equals("tc4") )
            return new FluxBrush();
        else if ( criteria.equals("tc5") )
            return new FluxRemover();
        else if ( criteria.equals("tc6") )
            return new GlazingHammer();
        else if ( criteria.equals("tc7") )
            return new GrindingStone();
        else if ( criteria.equals("tc8") )
            return new GrozingPliers();
        else if ( criteria.equals("tc9") )
            return new Lathekin();
        else if ( criteria.equals("tc10") )
            return new LensCutter();
        else if ( criteria.equals("tc11") )
            return new RunningPliers();
        else if ( criteria.equals("tc12") )
            return new TapWheel();

        return null;
    }
}
