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
package org.orcid.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.orcid.core.exception.DuplicatedGroupIdRecordException;
import org.orcid.core.exception.GroupIdRecordNotFoundException;
import org.orcid.core.exception.OrcidElementCantBeDeletedException;
import org.orcid.core.locale.LocaleManager;
import org.orcid.core.manager.GroupIdRecordManager;
import org.orcid.core.manager.OrcidSecurityManager;
import org.orcid.core.manager.SourceManager;
import org.orcid.core.manager.read_only.impl.GroupIdRecordManagerReadOnlyImpl;
import org.orcid.core.manager.validator.ActivityValidator;
import org.orcid.jaxb.model.common_rc4.Source;
import org.orcid.jaxb.model.common_rc4.SourceClientId;
import org.orcid.jaxb.model.common_rc4.SourceOrcid;
import org.orcid.jaxb.model.groupid_rc4.GroupIdRecord;
import org.orcid.persistence.jpa.entities.GroupIdRecordEntity;
import org.orcid.persistence.jpa.entities.SourceEntity;

public class GroupIdRecordManagerImpl extends GroupIdRecordManagerReadOnlyImpl implements GroupIdRecordManager {

    @Resource
    private SourceManager sourceManager;

    @Resource
    private LocaleManager localeManager;

    @Resource
    private OrcidSecurityManager orcidSecurityManager;

    @Resource
    private ActivityValidator activityValidator;

    @Override
    public GroupIdRecord createGroupIdRecord(GroupIdRecord groupIdRecord) {
        SourceEntity sourceEntity = sourceManager.retrieveSourceEntity();
        activityValidator.validateGroupIdRecord(groupIdRecord, true, sourceEntity);
        validateDuplicate(groupIdRecord);
        if (sourceEntity != null) {
            Source source = new Source();
            if (sourceEntity.getSourceClient() != null) {
                source.setSourceClientId(new SourceClientId(sourceEntity.getSourceClient().getClientId()));
            } else if (sourceEntity.getSourceProfile() != null) {
                source.setSourceOrcid(new SourceOrcid(sourceEntity.getSourceProfile().getId()));
            }
            groupIdRecord.setSource(source);
        }

        GroupIdRecordEntity entity = jpaJaxbGroupIdRecordAdapter.toGroupIdRecordEntity(groupIdRecord);
        groupIdRecordDao.persist(entity);
        return jpaJaxbGroupIdRecordAdapter.toGroupIdRecord(entity);
    }

    @Override
    public GroupIdRecord updateGroupIdRecord(Long putCode, GroupIdRecord groupIdRecord) {
        GroupIdRecordEntity existingEntity = groupIdRecordDao.find(putCode);

        if (existingEntity == null) {
            throw new GroupIdRecordNotFoundException();
        }

        SourceEntity sourceEntity = sourceManager.retrieveSourceEntity();
        // Save the original source
        String existingSourceId = existingEntity.getSourceId();
        String existingClientSourceId = existingEntity.getClientSourceId();

        activityValidator.validateGroupIdRecord(groupIdRecord, false, sourceEntity);
        validateDuplicate(groupIdRecord);

        orcidSecurityManager.checkSource(existingEntity);
        GroupIdRecordEntity updatedEntity = jpaJaxbGroupIdRecordAdapter.toGroupIdRecordEntity(groupIdRecord);
        updatedEntity.setDateCreated(existingEntity.getDateCreated());
        // Be sure it doesn't overwrite the source
        updatedEntity.setSourceId(existingSourceId);
        updatedEntity.setClientSourceId(existingClientSourceId);

        updatedEntity = groupIdRecordDao.merge(updatedEntity);
        return jpaJaxbGroupIdRecordAdapter.toGroupIdRecord(updatedEntity);
    }

    @Override
    public void deleteGroupIdRecord(Long putCode) {
        GroupIdRecordEntity existingEntity = groupIdRecordDao.find(putCode);
        if (existingEntity != null) {
            if (groupIdRecordDao.haveAnyPeerReview(existingEntity.getGroupId())) {
                throw new OrcidElementCantBeDeletedException("Unable to delete group id because there are peer reviews associated to it");
            }
            orcidSecurityManager.checkSource(existingEntity);
            groupIdRecordDao.remove(Long.valueOf(putCode));
        } else {
            throw new GroupIdRecordNotFoundException();
        }
    }

    private void validateDuplicate(GroupIdRecord newGroupIdRecord) {
        List<GroupIdRecordEntity> existingGroupIdRecords = groupIdRecordDao.getAll();
        if (existingGroupIdRecords != null && !existingGroupIdRecords.isEmpty()) {
            for (GroupIdRecordEntity existing : existingGroupIdRecords) {
                // Compare if it is a new element or if the element to compare
                // dont have the same put code than me
                if (newGroupIdRecord.getPutCode() == null || !newGroupIdRecord.getPutCode().equals(existing.getId())) {
                    if (newGroupIdRecord.getGroupId().equalsIgnoreCase(existing.getGroupId())) {
                        throw new DuplicatedGroupIdRecordException();
                    }
                }
            }
        }
    }

}
