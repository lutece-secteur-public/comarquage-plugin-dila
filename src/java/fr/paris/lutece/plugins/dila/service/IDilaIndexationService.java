package fr.paris.lutece.plugins.dila.service;

import fr.paris.lutece.plugins.dila.exception.DilaException;


/**
 * Indexation service
 */
public interface IDilaIndexationService
{
    /**
     * Index all files (create, update, delete)
     * @throws DilaException daemon error
     */
    void indexAll( ) throws DilaException;
}
