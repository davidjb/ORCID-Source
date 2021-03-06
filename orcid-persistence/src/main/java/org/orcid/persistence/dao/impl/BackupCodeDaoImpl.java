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
package org.orcid.persistence.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.orcid.persistence.dao.BackupCodeDao;
import org.orcid.persistence.jpa.entities.BackupCodeEntity;
import org.springframework.transaction.annotation.Transactional;

public class BackupCodeDaoImpl extends GenericDaoImpl<BackupCodeEntity, Long> implements BackupCodeDao {
    
    public BackupCodeDaoImpl() {
        super(BackupCodeEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BackupCodeEntity> getUnusedBackupCodes(String orcid) {
        Query query = entityManager.createQuery("FROM BackupCodeEntity WHERE orcid = :orcid AND usedDate IS NULL");
        query.setParameter("orcid", orcid);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void markUsed(String orcid, String hashedCode) {
        Query query = entityManager.createQuery("UPDATE BackupCodeEntity SET usedDate = now() WHERE orcid = :orcid AND hashedCode = :hashedCode");
        query.setParameter("orcid", orcid);
        query.setParameter("hashedCode", hashedCode);
        query.executeUpdate();
    }   
    
    @Override
    @Transactional
    public void removedUsedBackupCodes(String orcid) {
        Query query = entityManager.createQuery("DELETE FROM BackupCodeEntity WHERE orcid = :orcid AND usedDate IS NULL");
        query.setParameter("orcid", orcid);
        query.executeUpdate();
    }   
}
