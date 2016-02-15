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
import com.googlecode.objectify.Ref;

import java.lang.String;
import java.util.Date;
import java.util.List;

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
public class Attribute {
  @Parent com.googlecode.objectify.Ref<Problem> theProblem;
  @Id public Long id;

  public String attributeName;
  @Index public String attributeFieldName;
  @Index public Date date;

  /**
   * Simple constructor just sets the date
   **/
  public Attribute() {
    date = new Date();
  }

  /**
   * A connivence constructor
   **/
  public Attribute(Problem problem, String attributeName) {
    this();
    if( problem != null ) {
      theProblem = Ref.create(problem);  // Creating the Ancestor key
    } else {
      theProblem = Ref.create(new Problem("nullProblem"));
    }
    this.attributeName = attributeName;
  }

  /**
   * Takes all important fields
   **/
  public Attribute(Problem problem, String attributeName, String var_attributeFieldName) {
    this(problem, attributeName);
    attributeFieldName = var_attributeFieldName;
  }

  public String getAttributeName(){
	  return attributeName;
  }
  
  public Long getId() {
	  return id;
  }
}

