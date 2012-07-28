package com.synergy.smartv.source.api.converters;

import com.synergy.smartv.source.api.IContentSource;
import com.synergy.smartv.source.api.model.ContentSourceModel;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Collection;


public class ContentSourceConverter implements Converter<IContentSource, ContentSourceModel> {
  @Override
  public ContentSourceModel convert(final IContentSource contentSource) {
    Collection<String> subContentSources = new ArrayList<String>();
    if(contentSource.getCachedSubSources() != null) {
      for(IContentSource subContent:contentSource.getCachedSubSources().values()) {
        subContentSources.add(subContent.getDisplayName());
      }
    }
    return new ContentSourceModel(subContentSources, contentSource.getDisplayName());
  }
}
