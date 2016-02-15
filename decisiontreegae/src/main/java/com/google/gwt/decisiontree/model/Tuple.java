

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
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Index;

/**
 * The @Entity tells Objectify about our entity.  We also register it in
 * OfyHelper.java -- very important.
 *
 * This is never actually created, but gives a hint to Objectify about our Ancestor key.
 */
@Entity
public class Tuple {
  @Parent Ref<Problem> theProblem;
  @Index public String tuple_id;
  @Id public Long id;
  
public Tuple()
{
	String s="empty operator";
}
  
public Tuple(String sid, Problem problem)
{
	tuple_id = sid;
	try {
	     theProblem = Ref.create(problem);
	}
	catch (java.lang.NullPointerException e)
	{
		System.out.println(e.toString() + " tuple in datastore was not created because of Problem is empty" );
	}
}
}