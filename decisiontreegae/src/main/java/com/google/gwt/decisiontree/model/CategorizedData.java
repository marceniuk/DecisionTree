/**
 * Copyright 2014-2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.gwt.decisiontree.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.Key;

import java.lang.String;
import java.util.Date;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;


/**
 * The @Entity tells Objectify about our entity.  We also register it in OfyHelper.java -- very
 * important. Our primary key @Id is set automatically by the Google Datastore for us.
 *
 * We add a @Parent to tell the object about its ancestor. We are doing this to support many
 * guestbooks.  Objectify, unlike the AppEngine library requires that you specify the fields you
 * want to index using @Index.  This is often a huge win in performance -- though if you don't Index
 * your data from the start, you'll have to go back and index it later.
 *
 * NOTE - all the properties are PUBLIC so that can keep this simple, otherwise,
 * Jackson, wants us to write a BeanSerializaer for cloud endpoints.
 **/
@Entity
public class CategorizedData {
  @Load public Ref<Attribute> theAttribute;
  @Id Long id;
  @Parent public Key<Tuple> theTuple;
  public String attributeValue;
  
  @Index public Date date;

  
  /**
   * getter and setter for theAttribute
   **/
  
    public Attribute getAttribute() { return theAttribute.get(); }
    public void setAttribute(Attribute attribute) { theAttribute = Ref.create(attribute); }
  
  /**
   * Simple constructor just sets the date
   **/
  public CategorizedData() {
    date = new Date();
  }

  /**
   * A connivence constructor
   **/
  public CategorizedData(String tupleId, String attribute_field_name, String var_attributeValue) {
    this();
	Attribute attribute = ofy().load().type(Attribute.class).filter("attributeFieldName", attribute_field_name).first().now();
	System.out.println("tupleId = " + tupleId);
	Tuple tuple = ofy().load().type(Tuple.class).filter("tuple_id",tupleId).first().now();
	System.out.println("CD tuple.id = " + tuple.id);
	//Tuple tuple = new Tuple(tupleId, attribute.theProblem.get());
	
	if( tupleId != null ) {
      theTuple = Key.create(Tuple.class, tuple.id);          // Creating the Ancestor key
    } else {
      theTuple = Key.create(Tuple.class, (new Tuple()).id);
    }
    if( attribute != null ) {
      theAttribute = Ref.create(attribute);  // Creating the Ancestor ref
    } else {
      theAttribute = Ref.create(new Attribute());
    }
    this.attributeValue = var_attributeValue;
  }
    
}

  