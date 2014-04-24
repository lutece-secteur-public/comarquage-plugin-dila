/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.dila.business.fichelocale.dao.impl;

import fr.paris.lutece.plugins.dila.business.fichelocale.dao.IAudienceDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.AudienceDTO;
import fr.paris.lutece.plugins.dila.service.DilaPlugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.util.sql.DAOUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of {@link IAudienceDAO}
 */
public class AudienceDAO implements IAudienceDAO, Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = 5329588195127530638L;
    private static final String SQL_QUERY_SELECT_ALL = "SELECT id, label FROM dila_audience ORDER BY label ASC";

    @Override
    public List<AudienceDTO> findAll(  )
    {
        List<AudienceDTO> result = new ArrayList<AudienceDTO>(  );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        daoUtil.executeQuery(  );

        // Loop for each result
        while ( daoUtil.next(  ) )
        {
            AudienceDTO audience = new AudienceDTO(  );
            audience.setId( daoUtil.getLong( 1 ) );
            audience.setLabel( daoUtil.getString( 2 ) );

            result.add( audience );
        }

        daoUtil.free(  );

        return result;
    }
}
