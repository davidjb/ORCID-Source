/**
 * =============================================================================
 *
 * ORCID (R) Open Source
 * http://orcid.org
 *
 * Copyright (c) 2012-2014 ORCID, Inc.
 * Licensed under an MIT-Style License (MIT)
 * http://orcid.org/open-source-license
 *
 * This copyright and license information (including a link to the full license)
 * shall be included in its entirety in all copies or substantial portion of
 * the software.
 *
 * =============================================================================
 */
package org.orcid.core.manager;

import org.orcid.persistence.jpa.entities.MinimizedSourceEntity;

/**
 * @author Angel Montenegro
 * */
public interface SourceEntityCacheManager {

    public MinimizedSourceEntity retrieve(String id) throws IllegalArgumentException;
    
    public void put(MinimizedSourceEntity sourceEntity);
    
    public void removeAll();
    
    public void remove(String id);
    
}
