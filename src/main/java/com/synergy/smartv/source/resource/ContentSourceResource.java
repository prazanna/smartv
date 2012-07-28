package com.synergy.smartv.source.resource;

import com.synergy.smartv.source.api.IContentSource;
import com.synergy.smartv.source.api.model.ContentSourceModel;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.awt.event.ContainerEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Resource
@Component
@Path("/sources")
public class ContentSourceResource {
  @Autowired
  private WebDriver webDriver;
  private Map<String, IContentSource> contentSources = new HashMap<String, IContentSource>();
  @Autowired
  private ConversionService conversionService;

  @Autowired
  public ContentSourceResource(IContentSource[] contentSources) {
    for(IContentSource source:contentSources) {
      this.contentSources.put(source.getDisplayName(), source);
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public ContentSourceModel getBaseContentSource() {
    return new ContentSourceModel(contentSources.keySet(), "root_sources");
  }

  @GET
  @Path("{source_path: .+}")
  @Produces(MediaType.APPLICATION_JSON)
  public ContentSourceModel getContentSource(@PathParam("source_path") String path) {
    return walkPath(ContentSourcePath.parse(path));
  }

  private ContentSourceModel walkPath(final ContentSourcePath sourcePath) {
    IContentSource rootSource = contentSources.get(sourcePath.getRootContentSource());
    IContentSource source = rootSource.findContentSourceModel(sourcePath.getContentSource(), webDriver);
    return  conversionService.convert(source, ContentSourceModel.class);
  }
//
//  @GET
//  @Produces(MediaType.APPLICATION_JSON)
//  public ContentSourceList getContentSources() throws JSONException {
//    ContentSourceList listContent = new ContentSourceList();
//    for(IContentSource contentSource:contentSources) {
//      listContent.add(contentSource.getName());
//    }
//    return listContent;
//  }
//
//  @GET
//  @Path("{source_name}")
//  @Produces(MediaType.APPLICATION_JSON)
//  public ContentList getContents(@PathParam("source_name") String sourceName) {
//    ContentList contentList = new ContentList();
//    IContentSource contentSource = findContentSource(sourceName);
//    Collection<Viewable> viewables = contentSource.bringUpContentSource(webDriver);
//    Collection<String> contents = new ArrayList<String>();
//    for(Viewable viewable:viewables) {
//      contents.add(viewable.getName());
//    }
//    contentList.setSources(contents);
//    return contentList;
//  }
//
//  @GET
//  @Path("{source_name}/{content_name}")
//  @Produces(MediaType.APPLICATION_JSON)
//  public Response viewContent(@PathParam("source_name") String sourceName, @PathParam("content_name") String contentName) {
//    IContentSource contentSource = findContentSource(sourceName);
//    Collection<Viewable> viewables = contentSource.bringUpContentSource(webDriver);
//    Content viewable = findContent(viewables, contentName);
//    viewable.bringUpContent(webDriver);
//    return Response.ok().build();
//  }
//
//  private Content findContent(final Collection<Viewable> viewables, String name) {
//    for(Viewable viewable:viewables) {
//      if(name.equalsIgnoreCase(viewable.getName()) && viewable instanceof Content) {
//        return (Content) viewable;
//      }
//    }
//    return null;
//  }
//
//
//  private IContentSource findContentSource(final String sourceName) {
//    for(IContentSource contentSource:contentSources) {
//      if(sourceName.equals(contentSource.getName())) {
//        return contentSource;
//      }
//    }
//    return null;
//  }
}
