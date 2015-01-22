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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.07.22 at 06:46:00 PM BST 
//

package org.orcid.jaxb.model.notification.custom;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import org.orcid.jaxb.model.common.OrcidId;
import org.orcid.jaxb.model.common.Source;
import org.orcid.jaxb.model.notification.NotificationType;
import org.orcid.jaxb.model.notification.custom.NotificationCustom;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the org.orcid.jaxb.model.notification package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _NotificationType_QNAME = new QName("http://www.orcid.org/ns/orcid", "notificationType");
    private final static QName _SourceName_QNAME = new QName("http://www.orcid.org/ns/orcid", "sourceName");
    private final static QName _Host_QNAME = new QName("http://www.orcid.org/ns/orcid", "host");

    /**
     * Create a new ObjectFactory that can be used to create new instances of
     * schema derived classes for package: org.orcid.jaxb.model.notification.custom
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Source }
     * 
     */
    public Source createSource() {
        return new Source();
    }

    /**
     * Create an instance of {@link OrcidId }
     * 
     */
    public OrcidId createOrcidId() {
        return new OrcidId();
    }

    /**
     * Create an instance of {@link NotificationAddActivities }
     * 
     */
    public NotificationCustom createNotificationCustom() {
        return new NotificationCustom();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}
     * {@link NotificationType }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.orcid.org/ns/orcid", name = "notificationType")
    public JAXBElement<NotificationType> createNotificationType(NotificationType value) {
        return new JAXBElement<NotificationType>(_NotificationType_QNAME, NotificationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.orcid.org/ns/orcid", name = "sourceName")
    public JAXBElement<String> createSourceName(String value) {
        return new JAXBElement<String>(_SourceName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.orcid.org/ns/orcid", name = "host")
    public JAXBElement<String> createHost(String value) {
        return new JAXBElement<String>(_Host_QNAME, String.class, null, value);
    }

}
