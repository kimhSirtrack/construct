package construct;

//from construct import Struct, MetaField, StaticField, FormatField
//from construct import Container, Byte
//from construct import FieldError, SizeofError

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static construct.Core.*;
import static construct.Adapters.*;
import static construct.Macros.*;
import construct.Core.*;
import construct.Macros.*;
import construct.exception.FieldError;
import junit.framework.TestCase;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import construct.exception.ValueError;

public class MacrosTest  
{
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void BitIntegerAdapterTest() {
  	Construct bw;
    
    bw = Bitwise( Field("bitwise",8) );
    assertArrayEquals( new byte[]{1,1,1,1,1,1,1,1}, (byte[]) bw.parse( new byte[]{ (byte) 0xFF } ));
    

/*    [Bitwise(Field("bitwise", 8)).parse, "\xff", "\x01" * 8, None],
    [Bitwise(Field("bitwise", lambda ctx: 8)).parse, "\xff", "\x01" * 8, None],
    [Bitwise(Field("bitwise", 8)).build, "\x01" * 8, "\xff", None],
    [Bitwise(Field("bitwise", lambda ctx: 8)).build, "\x01" * 8, "\xff", None],
*/
  /*  ba = BitIntegerAdapter( Field("bitintegeradapter", 8), 8 );
    assertEquals( 255, ba.parse( new byte[]{1,1,1,1,1,1,1,1} ));

    ba = BitIntegerAdapter( Field("bitintegeradapter", 8), 8, false, true );
    assertEquals( -1, ba.parse( new byte[]{1,1,1,1,1,1,1,1} ));

    ba = BitIntegerAdapter( Field("bitintegeradapter", 8), 8, true, false, 4 );
    assertEquals( 0x0f, ba.parse( new byte[]{1,1,1,1,0,0,0,0} ));

    ba = BitIntegerAdapter( Field("bitintegeradapter", 8), 8 );
    assertArrayEquals( new byte[]{1,1,1,1,1,1,1,1}, ba.build(255) );

    exception.expect( BitIntegerError.class );
    ba = BitIntegerAdapter( Field("bitintegeradapter", 8), 8 );
    assertEquals( null, ba.build(-1) );

    ba = BitIntegerAdapter( Field("bitintegeradapter", 8), 8, false, true );
    assertArrayEquals( new byte[]{1,1,1,1,1,1,1,1}, ba.build(-1) );

    ba = BitIntegerAdapter( Field("bitintegeradapter", 8), 8, true, false, 4 );
    assertArrayEquals( new byte[]{1,1,1,1,0,0,0,0}, ba.build(0x0f) );
*/
  }

}
