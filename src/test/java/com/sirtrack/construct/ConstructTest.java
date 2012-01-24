package com.sirtrack.construct;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.sirtrack.construct.Core.RangeError;
import com.sirtrack.construct.lib.Containers.Container;


import static com.sirtrack.construct.Core.*;
import static com.sirtrack.construct.Macros.*;
import static com.sirtrack.construct.lib.Containers.*;

public class ConstructTest  
{
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void rangeTest(){
  	Range range;
  	
  	range = Range(3,5, UBInt8("range"));
  	assertEquals( ListContainer(1,2,3), range.parse( ByteArray(1,2,3)));
  	assertEquals( ListContainer(1,2,3,4), range.parse( ByteArray(1,2,3,4)));
  	assertEquals( ListContainer(1,2,3,4,5), range.parse( ByteArray(1,2,3,4,5)));

  	assertArrayEquals( ByteArray(1,2,3), range.build( ListContainer(1,2,3)));
  	assertArrayEquals( ByteArray(1,2,3,4), range.build( ListContainer(1,2,3,4)));
  	assertArrayEquals( ByteArray(1,2,3,4,5), range.build( ListContainer(1,2,3,4,5)));

  	exception.expect( RangeError.class );
  	range.build( ListContainer(1,2,3,4,5,6));

  	exception.expect( RangeError.class );
  	range.build( ListContainer(1,2));
  			
  	exception.expect( RangeError.class );
  	range.parse( ByteArray(1,2));
  	
  /*
    [Range(3, 5, UBInt8("range")).build, [1,2,3,4,5,6], None, RangeError],
   */
  }
  
  @Test
  public void structTest() {
  	  Struct struct, foo;
  	  Container ca, cb;
  	  byte[] ba;
  	  
  	  struct = Struct( "struct", UBInt8("a"), UBInt16("b") );
  	  ca = struct.parse( ByteArray(1,0,2) );
  	  cb = Container( "a", 1, "b", 2 );
      assertTrue( ca.equals(cb) );

      struct = Struct( "struct", UBInt8("a"), UBInt16("b"), 
  	  									Struct( "foo", UBInt8("c"), UBInt8("d") ));
  	  ca = struct.parse( ByteArray(1,0,2,3,4) );
  	  cb = Container( "a",1, "b",2, "foo", Container( "c",3,"d",4));
      assertTrue( ca.equals(cb) );

      struct = Struct( "struct", UBInt8("a"), UBInt16("b"));
  	  ba = struct.build( Container( "a",1, "b", 2));
  	  assertArrayEquals( ByteArray(1,0,2), ba );

  	  foo = Struct( "foo", UBInt8("c"), UBInt8("d") );
  	  struct = Struct( "struct", UBInt8("a"), UBInt16("b"), foo );
  	  ba = struct.build( Container( "a",1, "b", 2, "foo", Container("c", 3, "d",4)));
  	  assertArrayEquals( ByteArray(1,0,2,3,4), ba );
  	  
  	  struct = Struct( "struct", UBInt8("a"), UBInt16("b"), Embedded( Struct("foo", UBInt8("c"), UBInt8("d"))));
  	  ca = struct.parse( ByteArray(1,0,2,3,4) );
  	  cb = Container( "a", 1, "b", 2, "c", 3, "d", 4 );
  	  assertEquals( cb, ca );

  	  struct = Struct( "struct", UBInt8("a"), UBInt16("b"), Embedded( Struct("foo", UBInt8("c"), UBInt8("d"))));
  	  ba = struct.build( Container( "a", 1, "b", 2, "c", 3, "d", 4 ));
  	  assertArrayEquals( ByteArray(1,0,2,3,4), ba );
	  
  }
  
  @Test
  public void switchTest(){
  	Switch switchstruct;
  	
  	switchstruct = Switch("switch", new KeyFunc(){ public Object key(Container context){return 5;}}, 
  			                  1, UBInt8("x"),5, UBInt16("y"));
  	assertEquals( 2, switchstruct.parse(ByteArray(0,2)));

  	switchstruct = Switch("switch", new KeyFunc(){ public Object key(Container context){return 6;}}, 
										Container( 1, UBInt8("x"),5, UBInt16("y")),
				UBInt8("x"), false);
  	assertEquals( 0, switchstruct.parse(ByteArray(0,2)));

  	switchstruct = Switch("switch", new KeyFunc(){ public Object key(Container context){return 5;}}, 
				Container( 1, UBInt8("x"), 5, UBInt16("y")),
				NoDefault, true);
  	assertEquals( Container(5,2), switchstruct.parse(ByteArray(0,2)));

  	switchstruct = Switch("switch", new KeyFunc(){ public Object key(Container context){return 5;}}, 
				1, UBInt8("x"), 5, UBInt16("y"));
  	assertArrayEquals( ByteArray(0,2), switchstruct.build(2));

  	switchstruct = Switch("switch", new KeyFunc(){ public Object key(Container context){return 6;}}, 
				Container( 1, UBInt8("x"), 5, UBInt16("y")),
				UBInt8("x"), false);
  	assertArrayEquals( ByteArray(9), switchstruct.build(9));

  	switchstruct = Switch("switch", new KeyFunc(){ public Object key(Container context){return 6;}}, 
				Container( 1, UBInt8("x"), 5, UBInt16("y")),
				NoDefault, true);
  	assertArrayEquals( ByteArray(0,2), switchstruct.build(ListContainer(5,2)));

  	switchstruct = Switch("switch", new KeyFunc(){ public Object key(Container context){return 6;}}, 
				Container( 1, UBInt8("x"), 5, UBInt16("y")),
				NoDefault, true);
  	exception.expect( SwitchError.class );
  	switchstruct.build(ListContainer(89,2));
  	
  	switchstruct = Switch("switch", new KeyFunc(){ public Object key(Container context){return 6;}}, 
				1, UBInt8("x"), 5, UBInt16("y"));
  	exception.expect( SwitchError.class );
  	switchstruct.build(9);
  	
  	switchstruct = Switch("switch", new KeyFunc(){ public Object key(Container context){return 6;}}, 
  												Container( 1, UBInt8("x"), 5, UBInt16("y")));
  	exception.expect( SwitchError.class );
  	switchstruct.parse(ByteArray(0,2));
  }
  
  @Test
  public void reconfigTest(){
  	Container c1 = Container("foo",1);
  	Object c2 = Struct("reconfig", Reconfig("foo", UBInt8("bar"))).parse(ByteArray(1));
  	assertEquals( c1, c2 );
  	
  	byte[] ba = Struct("reconfig", Reconfig("foo", UBInt8("bar"))).build( Container("foo", 1));
  	assertArrayEquals( ByteArray(1), ba);
  }
  
  @Test
  public void valueTest(){
  	Value val = Value("value", new ValueFunc(){public Object get(Container ctx) {return "moo";}});
  	assertEquals( "moo", val.parse(""));
  	assertArrayEquals( new byte[0], val.build(null));
  }
}
