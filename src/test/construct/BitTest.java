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
import static construct.lib.Containers.*;
import construct.exception.FieldError;
import junit.framework.TestCase;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import construct.exception.ValueError;

public class BitTest  
{
  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Test
  public void TestBitStruct() {
  	Construct struct = BitStruct("foo",
                        BitField("a", 3),
                        Flag("b"),
                        Padding(3),
                        Nibble("c"),
                        BitField("d", 5)
    									);

  	Object o = struct.parse(new byte[]{(byte)0xe1, 0x1f});
  	assertTrue( Container( P("a", 7), P("b", false), P("c",8), P("d", 31 )).equals(o) );
  	
    /*
        def test_parse(self):
            self.assertEqual(struct.parse("\xe1\x1f"),
                Container(a=7, b=False, c=8, d=31))

        def test_parse_nested(self):
            struct = BitStruct("foo",
                BitField("a", 3),
                Flag("b"),
                Padding(3),
                Nibble("c"),
                Struct("bar",
                    Nibble("d"),
                    Bit("e"),
                )
            )
            self.assertEqual(struct.parse("\xe1\x1f"),
                Container(a=7, b=False, bar=Container(d=15, e=1), c=8))

     */

    }

}

