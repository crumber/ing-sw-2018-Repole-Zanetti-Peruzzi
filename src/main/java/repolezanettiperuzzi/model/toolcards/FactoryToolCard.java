package repolezanettiperuzzi.model.toolcards;

/**
 * Classe factory che svolge la costruzione corretta della tool card
 * @author Andrea Zanetti
 */
public class FactoryToolCard {

    /**
     *
     * @param criteria stringa che identifica una tool card
     * @return costruttore della tool card associata alla stringa passata
     */
    public static ToolCard getToolCard(String criteria)
    {
        if ( criteria.equals("tc1") )
            return new GrozingPliers();
        else if ( criteria.equals("tc2") )
            return new EglomiseBrush();
        else if ( criteria.equals("tc3") )
            return new CopperFoilBurnisher();
        else if ( criteria.equals("tc4") )
            return new Lathekin();
        else if ( criteria.equals("tc5") )
            return new LensCutter();
        else if ( criteria.equals("tc6") )
            return new FluxBrush();
        else if ( criteria.equals("tc7") )
            return new GlazingHammer();
        else if ( criteria.equals("tc8") )
            return new RunningPliers();
        else if ( criteria.equals("tc9") )
            return new CorkbackedStraightedge();
        else if ( criteria.equals("tc10") )
            return new GrindingStone();
        else if ( criteria.equals("tc11") )
            return new FluxRemover();
        else if ( criteria.equals("tc12") )
            return new TapWheel();
        else
            return null;
    }
}
