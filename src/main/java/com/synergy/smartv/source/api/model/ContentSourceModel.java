package com.synergy.smartv.source.api.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@XmlRootElement
public class ContentSourceModel {
  private Collection<String> subContentSources = new ArrayList<String>();
  private String displayName;

  public ContentSourceModel(final Collection<String> subContentSources, final String displayName) {
    this.subContentSources = subContentSources;
    this.displayName = displayName;
  }

  public ContentSourceModel() {
  }

  @XmlElement( name="source" )
  @XmlElementWrapper( name="sources" )
  public Collection<String> getSubContentSources() {
    return subContentSources;
  }

  public String getDisplayName() {
    return displayName;
  }

  @Override
  public String toString() {
    return "ContentSourceModel{" +
        "subContentSources=" + subContentSources +
        ", displayName='" + displayName + '\'' +
        '}';
  }
}
