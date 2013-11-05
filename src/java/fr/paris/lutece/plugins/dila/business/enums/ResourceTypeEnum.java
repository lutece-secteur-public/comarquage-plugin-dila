package fr.paris.lutece.plugins.dila.business.enums;

/**
 * Enum for all resource types
 */
public enum ResourceTypeEnum
{
    CARD( "Fiche", 1L ), RESOURCE( "Ressource", 3L ), Q_AND_A( "Question-réponse", 2L ), FOLDER( "Dossier", 2L ), SUBTHEME(
            "Sous-thème", 2L ), HOW_TO( "Comment faire si", 2L ), THEME( "Thème", 2L ), HOW_TO_HOME(
            "Accueil Comment faire si", 2L );

    /**
     * the type resource label
     */
    private String _strLabel;

    /**
     * the content type
     */
    private Long _lContentType;

    /**
     * Constructor
     * @param strLabel the type resource label
     * @param lContentType the content type
     */
    private ResourceTypeEnum( String strLabel, Long lContentType )
    {
        _strLabel = strLabel;
        _lContentType = lContentType;
    }

    /**
     * Get enum from label
     * @param strLabel label to find
     * @return the corresponding {@link ResourceTypeEnum} or null
     */
    public static ResourceTypeEnum fromLabel( String strLabel )
    {
        for ( ResourceTypeEnum e : ResourceTypeEnum.values( ) )
        {
            if ( e.getLabel( ).equals( strLabel ) )
            {
                return e;
            }
        }
        return null;
    }

    /**
     * 
     * @return _strLabel the label
     */
    public String getLabel( )
    {
        return _strLabel;
    }

    /**
     * 
     * @return _lContentType the content type
     */
    public Long getContentType( )
    {
        return _lContentType;
    }
}
