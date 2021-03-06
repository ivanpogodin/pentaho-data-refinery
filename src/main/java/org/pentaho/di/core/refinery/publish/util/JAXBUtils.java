/*! ******************************************************************************
 *
 * Pentaho Community Edition Project: data-refinery-pdi-plugin
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
 *
 * *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ********************************************************************************/

package org.pentaho.di.core.refinery.publish.util;

import com.sun.jersey.api.json.JSONJAXBContext;
import com.sun.jersey.api.json.JSONMarshaller;
import com.sun.jersey.api.json.JSONUnmarshaller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author Rowell Belen
 */
public class JAXBUtils {

  public static String marshallToXml( Object source ) throws Exception {
    JAXBContext jaxbContext = JAXBContext.newInstance( source.getClass() );
    Marshaller marshaller = jaxbContext.createMarshaller();
    marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
    StringWriter writer = new StringWriter();
    marshaller.marshal( source, writer );
    return writer.toString();
  }

  @SuppressWarnings( "unchecked" )
  public static <T> T unmarshalFromXml( final String xml, Class<T> destinationClass ) throws Exception {
    JAXBContext jaxbContext = JAXBContext.newInstance( destinationClass );
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    StringReader reader = new StringReader( xml );
    return (T) unmarshaller.unmarshal( reader );
  }

  public static String marshallToJson( Object source ) throws Exception {
    JAXBContext jaxbContext = JAXBContext.newInstance( source.getClass() );
    Marshaller marshaller = jaxbContext.createMarshaller();
    marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
    JSONMarshaller jsonMarshaller = JSONJAXBContext.getJSONMarshaller( marshaller, jaxbContext );
    StringWriter writer = new StringWriter();
    jsonMarshaller.marshallToJSON( source, writer );
    return writer.toString();
  }

  public static <T> T unmarshalFromJson( final String json, Class<T> destinationClass ) throws Exception {
    JAXBContext jaxbContext = JAXBContext.newInstance( destinationClass );
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    JSONUnmarshaller jsonUnmarshaller = JSONJAXBContext.getJSONUnmarshaller( unmarshaller, jaxbContext );
    StringReader reader = new StringReader( json );
    JAXBElement<T> element = jsonUnmarshaller.unmarshalJAXBElementFromJSON( reader, destinationClass );
    return element.getValue();
  }

}
