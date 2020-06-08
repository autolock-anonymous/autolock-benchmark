package uma.SMC;
import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
public class PluralLexer extends Lexer {
  public static final int PUNCTUATION=31;
  public static final int CASES=32;
  public static final int PARAM=24;
  public static final int EQUALOPERATOR=20;
  public static final int EVERYTHING=56;
  public static final int IMMUTABLE=12;
  public static final int PUBLIC_BEHAVIOR=9;
  public static final int JMLEND=52;
  public static final int AND=28;
  public static final int ID=61;
  public static final int EOF=-1;
  public static final int USEFIELDS=30;
  public static final int STATES=39;
  public static final int ENSURES=26;
  public static final int QUOTE=27;
  public static final int PURE=11;
  public static final int AT_UNIQUE=8;
  public static final int NAME=41;
  public static final int GREATER=47;
  public static final int FULL=10;
  public static final int IN=21;
  public static final int RSBRACKET=17;
  public static final int EQUAL=19;
  public static final int LCBRACKET=33;
  public static final int LESS=45;
  public static final int THIS=22;
  public static final int REFINE=36;
  public static final int NOTHING=55;
  public static final int SHARE=13;
  public static final int LESSTHANEQUAL=46;
  public static final int AT_IMMUTABLE=6;
  public static final int RCBRACKET=34;
  public static final int CLASS_STATES=35;
  public static final int AT_PURE=5;
  public static final int NUMBERS=62;
  public static final int ASSIGNABLE=54;
  public static final int AT_FULL=4;
  public static final int UNIQUE=14;
  public static final int STATE=38;
  public static final int GHOST=57;
  public static final int INV=42;
  public static final int OPERATOR=43;
  public static final int INVARIANT=59;
  public static final int OLD=60;
  public static final int RESULT=23;
  public static final int SEMICOLON=44;
  public static final int INT=58;
  public static final int VALUE=37;
  public static final int JMLSTART=51;
  public static final int PLUSMINUSOPERATOR=53;
  public static final int LSBRACKET=16;
  public static final int WS=63;
  public static final int REQUIRES=25;
  public static final int NONE=15;
  public static final int OR=50;
  public static final int DIM=40;
  public static final int AT_SHARE=7;
  public static final int USE=29;
  public static final int ANDD=49;
  public static final int PERM=18;
  public static final int GREATERTHANEQUAL=48;
  public PluralLexer(){
    ;
  }
  public PluralLexer(  CharStream input){
    this(input,new RecognizerSharedState());
  }
  public PluralLexer(  CharStream input,  RecognizerSharedState state){
    super(input,state);
  }
  public String getGrammarFileName(){
    return "/Users/ayeshasadiq/Documents/workspace/permission-specs/pt/uma/Plural/grammar/Plural.g";
  }
  public final void mATFULL() throws RecognitionException {
    try {
      int _type=AT_FULL;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        match("@Full");
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mATPURE() throws RecognitionException {
    try {
      int _type=AT_PURE;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        match("@Pure");
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mATIMMUTABLE() throws RecognitionException {
    try {
      int _type=AT_IMMUTABLE;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        match("@Immutable");
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mATSHARE() throws RecognitionException {
    try {
      int _type=AT_SHARE;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        match("@Share");
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mATUNIQUE() throws RecognitionException {
    try {
      int _type=AT_UNIQUE;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        match("@Unique");
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mPUBLICBEHAVIOR() throws RecognitionException {
    try {
      int _type=PUBLIC_BEHAVIOR;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        match("public_behavior");
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mFULL() throws RecognitionException {
    try {
      int _type=FULL;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        if (input.LA(1) == 'F' || input.LA(1) == 'f') {
          input.consume();
        }
 else {
          MismatchedSetException mse=new MismatchedSetException(null,input);
          recover(mse);
          throw mse;
        }
        match("ull");
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mPURE() throws RecognitionException {
    try {
      int _type=PURE;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        if (input.LA(1) == 'P' || input.LA(1) == 'p') {
          input.consume();
        }
 else {
          MismatchedSetException mse=new MismatchedSetException(null,input);
          recover(mse);
          throw mse;
        }
        match("ure");
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mIMMUTABLE() throws RecognitionException {
    try {
      int _type=IMMUTABLE;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        if (input.LA(1) == 'I' || input.LA(1) == 'i') {
          input.consume();
        }
 else {
          MismatchedSetException mse=new MismatchedSetException(null,input);
          recover(mse);
          throw mse;
        }
        match("mmutable");
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mSHARE() throws RecognitionException {
    try {
      int _type=SHARE;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        if (input.LA(1) == 'S' || input.LA(1) == 's') {
          input.consume();
        }
 else {
          MismatchedSetException mse=new MismatchedSetException(null,input);
          recover(mse);
          throw mse;
        }
        match("hare");
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mUNIQUE() throws RecognitionException {
    try {
      int _type=UNIQUE;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        if (input.LA(1) == 'U' || input.LA(1) == 'u') {
          input.consume();
        }
 else {
          MismatchedSetException mse=new MismatchedSetException(null,input);
          recover(mse);
          throw mse;
        }
        match("nique");
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mNONE() throws RecognitionException {
    try {
      int _type=NONE;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        if (input.LA(1) == 'N' || input.LA(1) == 'n') {
          input.consume();
        }
 else {
          MismatchedSetException mse=new MismatchedSetException(null,input);
          recover(mse);
          throw mse;
        }
        match("one");
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mLSBRACKET() throws RecognitionException {
    try {
      int _type=LSBRACKET;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        match('(');
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mRSBRACKET() throws RecognitionException {
    try {
      int _type=RSBRACKET;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        match(')');
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mPERM() throws RecognitionException {
    try {
      int _type=PERM;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        match('@');
        if (input.LA(1) == 'P' || input.LA(1) == 'p') {
          input.consume();
        }
 else {
          MismatchedSetException mse=new MismatchedSetException(null,input);
          recover(mse);
          throw mse;
        }
        match("erm");
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mEQUAL() throws RecognitionException {
    try {
      int _type=EQUAL;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        match('=');
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mEQUALOPERATOR() throws RecognitionException {
    try {
      int _type=EQUALOPERATOR;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        match("==");
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mIN() throws RecognitionException {
    try {
      int _type=IN;
      int _channel=DEFAULT_TOKEN_CHANNEL;
{
        match("in");
      }
      state.type=_type;
      state.channel=_channel;
    }
  finally {
    }
  }
  public final void mTHIS() throws RecognitionException {
    try {
      int _type=THIS;
      int _channel=DEFAULT_TOKEN_CHANNEL;
      int alt1=2;
      int LA1_0=input.LA(1);
      if ((LA1_0 == 't')) {
        int LA1_1=input.LA(2);
        if ((LA1_1 == 'h')) {
          int LA1_2=input.LA(3);
          if ((LA1_2 == 'i')) {
            int LA1_3=input.LA(4);
            if ((LA1_3 == 's')) {
              int LA1_4=input.LA(5);
              if ((LA1_4 == '!')) {
                alt1=2;
              }
 else {
                alt1=1;
              }
            }
 else {
              NoViableAltException nvae=new NoViableAltException("",1,3,input);
              throw nvae;
            }
          }
 else {
            NoViableAltException nvae=new NoViableAltException("",1,2,input);
            throw nvae;
          }
        }
 else {
          NoViableAltException nvae=new NoViableAltException("",1,1,input);
          throw nvae;
        }
      }
 else {
        NoViableAltException nvae=new NoViableAltException("",1,0,input);
        throw nvae;
      }
switch (alt1) {
case 1:
{
          match("this");
        }
      break;
case 2:
{
      match("this!fr");
    }
  break;
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mRESULT() throws RecognitionException {
try {
int _type=RESULT;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
if (input.LA(1) == 'R' || input.LA(1) == 'r') {
  input.consume();
}
 else {
  MismatchedSetException mse=new MismatchedSetException(null,input);
  recover(mse);
  throw mse;
}
match("esult");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mPARAM() throws RecognitionException {
try {
int _type=PARAM;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match('#');
loop2: do {
  int alt2=2;
  int LA2_0=input.LA(1);
  if (((LA2_0 >= '0' && LA2_0 <= '9'))) {
    alt2=1;
  }
switch (alt2) {
case 1:
{
      matchRange('0','9');
    }
  break;
default :
break loop2;
}
}
 while (true);
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mREQUIRES() throws RecognitionException {
try {
int _type=REQUIRES;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match("requires");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mENSURES() throws RecognitionException {
try {
int _type=ENSURES;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match("ensures");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mQUOTE() throws RecognitionException {
try {
int _type=QUOTE;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match('\"');
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mAND() throws RecognitionException {
try {
int _type=AND;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match('*');
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mUSE() throws RecognitionException {
try {
int _type=USE;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
if (input.LA(1) == 'U' || input.LA(1) == 'u') {
input.consume();
}
 else {
MismatchedSetException mse=new MismatchedSetException(null,input);
recover(mse);
throw mse;
}
match("se");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mUSEFIELDS() throws RecognitionException {
try {
int _type=USEFIELDS;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
if (input.LA(1) == 'U' || input.LA(1) == 'u') {
input.consume();
}
 else {
MismatchedSetException mse=new MismatchedSetException(null,input);
recover(mse);
throw mse;
}
match("se.FIELDS");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mPUNCTUATION() throws RecognitionException {
try {
int _type=PUNCTUATION;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match(',');
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mCASES() throws RecognitionException {
try {
int _type=CASES;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match('@');
if (input.LA(1) == 'C' || input.LA(1) == 'c') {
input.consume();
}
 else {
MismatchedSetException mse=new MismatchedSetException(null,input);
recover(mse);
throw mse;
}
match("ases");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mLCBRACKET() throws RecognitionException {
try {
int _type=LCBRACKET;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match('{');
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mRCBRACKET() throws RecognitionException {
try {
int _type=RCBRACKET;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match('}');
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mCLASSSTATES() throws RecognitionException {
try {
int _type=CLASS_STATES;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match('@');
if (input.LA(1) == 'C' || input.LA(1) == 'c') {
input.consume();
}
 else {
MismatchedSetException mse=new MismatchedSetException(null,input);
recover(mse);
throw mse;
}
match("lass");
if (input.LA(1) == 'S' || input.LA(1) == 's') {
input.consume();
}
 else {
MismatchedSetException mse=new MismatchedSetException(null,input);
recover(mse);
throw mse;
}
match("tates");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mREFINE() throws RecognitionException {
try {
int _type=REFINE;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match('@');
if (input.LA(1) == 'R' || input.LA(1) == 'r') {
input.consume();
}
 else {
MismatchedSetException mse=new MismatchedSetException(null,input);
recover(mse);
throw mse;
}
match("efine");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mVALUE() throws RecognitionException {
try {
int _type=VALUE;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
if (input.LA(1) == 'V' || input.LA(1) == 'v') {
input.consume();
}
 else {
MismatchedSetException mse=new MismatchedSetException(null,input);
recover(mse);
throw mse;
}
match("alue");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mSTATE() throws RecognitionException {
try {
int _type=STATE;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match('@');
if (input.LA(1) == 'S' || input.LA(1) == 's') {
input.consume();
}
 else {
MismatchedSetException mse=new MismatchedSetException(null,input);
recover(mse);
throw mse;
}
match("tate");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mSTATES() throws RecognitionException {
try {
int _type=STATES;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match('@');
if (input.LA(1) == 'S' || input.LA(1) == 's') {
input.consume();
}
 else {
MismatchedSetException mse=new MismatchedSetException(null,input);
recover(mse);
throw mse;
}
match("tates");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mDIM() throws RecognitionException {
try {
int _type=DIM;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
if (input.LA(1) == 'D' || input.LA(1) == 'd') {
input.consume();
}
 else {
MismatchedSetException mse=new MismatchedSetException(null,input);
recover(mse);
throw mse;
}
match("im");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mNAME() throws RecognitionException {
try {
int _type=NAME;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
if (input.LA(1) == 'N' || input.LA(1) == 'n') {
input.consume();
}
 else {
MismatchedSetException mse=new MismatchedSetException(null,input);
recover(mse);
throw mse;
}
match("ame");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mINV() throws RecognitionException {
try {
int _type=INV;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match("inv");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mOPERATOR() throws RecognitionException {
try {
int _type=OPERATOR;
int _channel=DEFAULT_TOKEN_CHANNEL;
int alt3=2;
int LA3_0=input.LA(1);
if ((LA3_0 == '=')) {
alt3=1;
}
 else if ((LA3_0 == '!')) {
alt3=2;
}
 else {
NoViableAltException nvae=new NoViableAltException("",3,0,input);
throw nvae;
}
switch (alt3) {
case 1:
{
match("==");
}
break;
case 2:
{
match("!=");
}
break;
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mSEMICOLON() throws RecognitionException {
try {
int _type=SEMICOLON;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match(';');
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mLESS() throws RecognitionException {
try {
int _type=LESS;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match('<');
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mLESSTHANEQUAL() throws RecognitionException {
try {
int _type=LESSTHANEQUAL;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match("<=");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mGREATER() throws RecognitionException {
try {
int _type=GREATER;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match('>');
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mGREATERTHANEQUAL() throws RecognitionException {
try {
int _type=GREATERTHANEQUAL;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match(">=");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mANDD() throws RecognitionException {
try {
int _type=ANDD;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match("&&");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mOR() throws RecognitionException {
try {
int _type=OR;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match("||");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mJMLSTART() throws RecognitionException {
try {
int _type=JMLSTART;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match("/*@");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mJMLEND() throws RecognitionException {
try {
int _type=JMLEND;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match("*/");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mPLUSMINUSOPERATOR() throws RecognitionException {
try {
int _type=PLUSMINUSOPERATOR;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
if (input.LA(1) == '+' || input.LA(1) == '-') {
input.consume();
}
 else {
MismatchedSetException mse=new MismatchedSetException(null,input);
recover(mse);
throw mse;
}
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mASSIGNABLE() throws RecognitionException {
try {
int _type=ASSIGNABLE;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match("assignable");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mNOTHING() throws RecognitionException {
try {
int _type=NOTHING;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match("\\nothing");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mEVERYTHING() throws RecognitionException {
try {
int _type=EVERYTHING;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match("\\everything");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mGHOST() throws RecognitionException {
try {
int _type=GHOST;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match("ghost");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mINT() throws RecognitionException {
try {
int _type=INT;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match("int");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mINVARIANT() throws RecognitionException {
try {
int _type=INVARIANT;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match("invariant");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mOLD() throws RecognitionException {
try {
int _type=OLD;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
match("\\old");
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mID() throws RecognitionException {
try {
int _type=ID;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
if ((input.LA(1) >= 'A' && input.LA(1) <= 'Z') || (input.LA(1) >= 'a' && input.LA(1) <= 'z')) {
input.consume();
}
 else {
MismatchedSetException mse=new MismatchedSetException(null,input);
recover(mse);
throw mse;
}
loop4: do {
int alt4=2;
int LA4_0=input.LA(1);
if ((LA4_0 == '.' || (LA4_0 >= '0' && LA4_0 <= '9') || (LA4_0 >= 'A' && LA4_0 <= 'Z') || LA4_0 == '_' || (LA4_0 >= 'a' && LA4_0 <= 'z'))) {
alt4=1;
}
switch (alt4) {
case 1:
{
if (input.LA(1) == '.' || (input.LA(1) >= '0' && input.LA(1) <= '9') || (input.LA(1) >= 'A' && input.LA(1) <= 'Z') || input.LA(1) == '_' || (input.LA(1) >= 'a' && input.LA(1) <= 'z')) {
input.consume();
}
 else {
MismatchedSetException mse=new MismatchedSetException(null,input);
recover(mse);
throw mse;
}
}
break;
default :
break loop4;
}
}
 while (true);
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mNUMBERS() throws RecognitionException {
try {
int _type=NUMBERS;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
loop5: do {
int alt5=2;
int LA5_0=input.LA(1);
if (((LA5_0 >= '0' && LA5_0 <= '9'))) {
alt5=1;
}
switch (alt5) {
case 1:
{
matchRange('0','9');
}
break;
default :
break loop5;
}
}
 while (true);
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public final void mWS() throws RecognitionException {
try {
int _type=WS;
int _channel=DEFAULT_TOKEN_CHANNEL;
{
int cnt6=0;
loop6: do {
int alt6=2;
int LA6_0=input.LA(1);
if (((LA6_0 >= '\t' && LA6_0 <= '\n') || LA6_0 == '\r' || LA6_0 == ' ')) {
alt6=1;
}
switch (alt6) {
case 1:
{
if ((input.LA(1) >= '\t' && input.LA(1) <= '\n') || input.LA(1) == '\r' || input.LA(1) == ' ') {
input.consume();
}
 else {
MismatchedSetException mse=new MismatchedSetException(null,input);
recover(mse);
throw mse;
}
}
break;
default :
if (cnt6 >= 1) break loop6;
EarlyExitException eee=new EarlyExitException(6,input);
throw eee;
}
cnt6++;
}
 while (true);
_channel=HIDDEN;
}
state.type=_type;
state.channel=_channel;
}
  finally {
}
}
public void mTokens() throws RecognitionException {
int alt7=60;
alt7=dfa7.predict(input);
switch (alt7) {
case 1:
{
mATFULL();
}
break;
case 2:
{
mATPURE();
}
break;
case 3:
{
mATIMMUTABLE();
}
break;
case 4:
{
mATSHARE();
}
break;
case 5:
{
mATUNIQUE();
}
break;
case 6:
{
mPUBLICBEHAVIOR();
}
break;
case 7:
{
mFULL();
}
break;
case 8:
{
mPURE();
}
break;
case 9:
{
mIMMUTABLE();
}
break;
case 10:
{
mSHARE();
}
break;
case 11:
{
mUNIQUE();
}
break;
case 12:
{
mNONE();
}
break;
case 13:
{
mLSBRACKET();
}
break;
case 14:
{
mRSBRACKET();
}
break;
case 15:
{
mPERM();
}
break;
case 16:
{
mEQUAL();
}
break;
case 17:
{
mEQUALOPERATOR();
}
break;
case 18:
{
mIN();
}
break;
case 19:
{
mTHIS();
}
break;
case 20:
{
mRESULT();
}
break;
case 21:
{
mPARAM();
}
break;
case 22:
{
mREQUIRES();
}
break;
case 23:
{
mENSURES();
}
break;
case 24:
{
mQUOTE();
}
break;
case 25:
{
mAND();
}
break;
case 26:
{
mUSE();
}
break;
case 27:
{
mUSEFIELDS();
}
break;
case 28:
{
mPUNCTUATION();
}
break;
case 29:
{
mCASES();
}
break;
case 30:
{
mLCBRACKET();
}
break;
case 31:
{
mRCBRACKET();
}
break;
case 32:
{
mCLASSSTATES();
}
break;
case 33:
{
mREFINE();
}
break;
case 34:
{
mVALUE();
}
break;
case 35:
{
mSTATE();
}
break;
case 36:
{
mSTATES();
}
break;
case 37:
{
mDIM();
}
break;
case 38:
{
mNAME();
}
break;
case 39:
{
mINV();
}
break;
case 40:
{
mOPERATOR();
}
break;
case 41:
{
mSEMICOLON();
}
break;
case 42:
{
mLESS();
}
break;
case 43:
{
mLESSTHANEQUAL();
}
break;
case 44:
{
mGREATER();
}
break;
case 45:
{
mGREATERTHANEQUAL();
}
break;
case 46:
{
mANDD();
}
break;
case 47:
{
mOR();
}
break;
case 48:
{
mJMLSTART();
}
break;
case 49:
{
mJMLEND();
}
break;
case 50:
{
mPLUSMINUSOPERATOR();
}
break;
case 51:
{
mASSIGNABLE();
}
break;
case 52:
{
mNOTHING();
}
break;
case 53:
{
mEVERYTHING();
}
break;
case 54:
{
mGHOST();
}
break;
case 55:
{
mINT();
}
break;
case 56:
{
mINVARIANT();
}
break;
case 57:
{
mOLD();
}
break;
case 58:
{
mID();
}
break;
case 59:
{
mNUMBERS();
}
break;
case 60:
{
mWS();
}
break;
}
}
protected DFA7 dfa7=new DFA7(this);
static final String DFA7_eotS="\1\45\1\uffff\7\44\2\uffff\1\73\3\44\1\uffff\2\44\1\uffff\1\101" + "\3\uffff\2\44\2\uffff\1\105\1\107\4\uffff\1\44\1\uffff\1\44\14\uffff" + "\4\44\1\130\5\44\2\uffff\4\44\2\uffff\2\44\4\uffff\1\44\3\uffff"+ "\1\44\5\uffff\4\44\1\155\1\156\1\uffff\2\44\1\162\2\44\1\uffff\5"+ "\44\1\172\2\44\1\uffff\1\44\1\177\1\u0080\2\44\2\uffff\3\44\1\uffff"+ "\1\u0086\1\u0087\1\u0088\4\44\1\uffff\2\44\1\uffff\1\44\2\uffff"+ "\2\44\1\u0093\2\44\3\uffff\3\44\1\u0099\1\44\1\u009b\1\u009d\3\44"+ "\1\uffff\1\u00a1\1\44\1\u00a3\2\44\1\uffff\1\44\3\uffff\3\44\1\uffff"+ "\1\44\1\uffff\1\44\1\u00ac\5\44\1\u00b2\1\uffff\2\44\1\u00b5\1\u00b6"+ "\1\44\1\uffff\2\44\2\uffff\1\u00ba\1\u00bb\1\44\2\uffff\3\44\1\u00c0"+ "\1\uffff";
static final String DFA7_eofS="\u00c1\uffff";
static final String DFA7_minS="\1\11\1\103\3\165\1\155\1\150\1\156\1\141\2\uffff\1\75\1\155\1\150" + "\1\145\1\uffff\1\145\1\156\1\uffff\1\57\3\uffff\1\141\1\151\2\uffff" + "\2\75\4\uffff\1\163\1\145\1\150\4\uffff\1\145\1\uffff\1\150\2\uffff"+ "\1\141\1\uffff\1\164\1\142\1\154\1\162\1\155\1\56\1\141\1\151\1"+ "\145\1\156\1\155\2\uffff\1\151\1\161\2\163\2\uffff\1\154\1\155\4"+ "\uffff\1\163\3\uffff\1\157\2\uffff\1\141\2\uffff\1\154\1\145\1\154"+ "\1\165\2\56\1\uffff\1\162\1\161\1\56\2\145\1\uffff\1\163\4\165\1"+ "\56\1\151\1\163\1\164\1\151\2\56\1\164\1\162\2\uffff\1\145\1\165"+ "\1\106\1\uffff\3\56\1\154\1\151\1\162\1\145\1\uffff\1\147\1\164"+ "\1\145\1\143\2\uffff\1\141\1\151\1\56\1\145\1\111\3\uffff\1\164"+ "\1\162\1\145\1\56\1\156\1\56\1\163\1\137\1\142\1\141\1\uffff\1\56"+ "\1\105\1\56\1\145\1\163\1\uffff\1\141\3\uffff\1\142\1\154\1\156"+ "\1\uffff\1\114\1\uffff\1\163\1\56\1\142\2\145\1\164\1\104\1\56\1"+ "\uffff\1\154\1\150\2\56\1\123\1\uffff\1\145\1\141\2\uffff\2\56\1"+ "\166\2\uffff\1\151\1\157\1\162\1\56\1\uffff";
static final String DFA7_maxS="\1\175\1\163\3\165\1\156\1\150\1\163\1\157\2\uffff\1\75\1\155\1" + "\150\1\145\1\uffff\1\145\1\156\1\uffff\1\57\3\uffff\1\141\1\151" + "\2\uffff\2\75\4\uffff\1\163\1\157\1\150\4\uffff\1\165\1\uffff\1"+ "\164\2\uffff\1\154\1\uffff\1\164\1\162\1\154\1\162\1\155\1\172\1"+ "\141\1\151\1\145\1\156\1\155\2\uffff\1\151\3\163\2\uffff\1\154\1"+ "\155\4\uffff\1\163\3\uffff\1\157\2\uffff\1\141\2\uffff\1\154\1\145"+ "\1\154\1\165\2\172\1\uffff\1\162\1\161\1\172\2\145\1\uffff\1\163"+ "\4\165\1\172\1\151\1\163\1\164\1\151\2\172\1\164\1\162\2\uffff\1"+ "\145\1\165\1\106\1\uffff\3\172\1\154\1\151\1\162\1\145\1\uffff\1"+ "\147\1\164\1\145\1\143\2\uffff\1\141\1\151\1\172\1\145\1\111\3\uffff"+ "\1\164\1\162\1\145\1\172\1\156\1\172\1\163\1\137\1\142\1\141\1\uffff"+ "\1\172\1\105\1\172\1\145\1\163\1\uffff\1\141\3\uffff\1\142\1\154"+ "\1\156\1\uffff\1\114\1\uffff\1\163\1\172\1\142\2\145\1\164\1\104"+ "\1\172\1\uffff\1\154\1\150\2\172\1\123\1\uffff\1\145\1\141\2\uffff"+ "\2\172\1\166\2\uffff\1\151\1\157\1\162\1\172\1\uffff";
static final String DFA7_acceptS="\11\uffff\1\15\1\16\4\uffff\1\25\2\uffff\1\30\1\uffff\1\34\1\36" + "\1\37\2\uffff\1\50\1\51\2\uffff\1\56\1\57\1\60\1\62\3\uffff\1\72" + "\1\73\1\74\1\1\1\uffff\1\3\1\uffff\1\5\1\17\1\uffff\1\41\13\uffff"+ "\1\21\1\20\4\uffff\1\61\1\31\2\uffff\1\53\1\52\1\55\1\54\1\uffff"+ "\1\64\1\65\1\71\1\uffff\1\2\1\4\1\uffff\1\35\1\40\6\uffff\1\22\5"+ "\uffff\1\21\16\uffff\1\47\1\67\3\uffff\1\32\7\uffff\1\45\4\uffff"+ "\1\10\1\7\5\uffff\1\14\1\46\1\23\12\uffff\1\12\5\uffff\1\42\1\uffff"+ "\1\66\1\44\1\43\3\uffff\1\13\1\uffff\1\24\10\uffff\1\27\5\uffff"+ "\1\26\2\uffff\1\11\1\70\3\uffff\1\33\1\63\4\uffff\1\6";
static final String DFA7_specialS="\u00c1\uffff}>";
static final String[] DFA7_transitionS={"\2\46\2\uffff\1\46\22\uffff\1\46\1\31\1\22\1\17\2\uffff\1\35" + "\1\uffff\1\11\1\12\1\23\1\40\1\24\1\40\1\uffff\1\37\13\uffff" + "\1\32\1\33\1\13\1\34\1\uffff\1\1\3\44\1\30\1\44\1\3\2\44\1\14"+ "\4\44\1\10\1\44\1\4\1\44\1\20\1\6\1\44\1\7\1\27\4\44\1\uffff"+ "\1\42\4\uffff\1\41\2\44\1\30\1\21\1\3\1\43\1\44\1\5\4\44\1\10"+ "\1\44\1\2\1\44\1\16\1\6\1\15\1\7\1\27\4\44\1\25\1\36\1\26","\1\55\2\uffff\1\47\2\uffff\1\51\6\uffff\1\50\1\uffff\1\56\1" + "\52\1\uffff\1\53\15\uffff\1\55\14\uffff\1\54\1\uffff\1\56\1" + "\57","\1\60","\1\61","\1\62","\1\63\1\64","\1\65","\1\66\4\uffff\1\67","\1\71\15\uffff\1\70","","","\1\72","\1\63","\1\74","\1\75","","\1\76","\1\77","","\1\100","","","","\1\102","\1\103","","","\1\104","\1\106","","","","","\1\110","\1\112\10\uffff\1\111\1\113","\1\114","","","","","\1\54\17\uffff\1\115","","\1\116\13\uffff\1\117","","","\1\120\12\uffff\1\121","","\1\117","\1\122\17\uffff\1\123","\1\124","\1\123","\1\125","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\23\44" + "\1\127\1\44\1\126\4\44","\1\131","\1\132","\1\133","\1\134","\1\135","","","\1\137","\1\141\1\uffff\1\140","\1\140","\1\142","","","\1\143","\1\144","","","","","\1\145","","","","\1\146","","","\1\147","","","\1\150","\1\151","\1\152","\1\153","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\1\154" + "\31\44","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","","\1\157","\1\160","\1\161\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32" + "\44","\1\163","\1\164","","\1\165","\1\166","\1\167","\1\170","\1\171","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","\1\173","\1\174","\1\175","\1\176","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","\1\u0081","\1\u0082","","","\1\u0083","\1\u0084","\1\u0085","","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","\1\u0089","\1\u008a","\1\u008b","\1\u008c","","\1\u008d","\1\u008e","\1\u008f","\1\u0090","","","\1\u0091","\1\u0092","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","\1\u0094","\1\u0095","","","","\1\u0096","\1\u0097","\1\u0098","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","\1\u009a","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","\1\u009c","\1\u009e","\1\u009f","\1\u00a0","","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","\1\u00a2","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","\1\u00a4","\1\u00a5","","\1\u00a6","","","","\1\u00a7","\1\u00a8","\1\u00a9","","\1\u00aa","","\1\u00ab","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","\1\u00ad","\1\u00ae","\1\u00af","\1\u00b0","\1\u00b1","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","","\1\u00b3","\1\u00b4","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","\1\u00b7","","\1\u00b8","\1\u00b9","","","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44","\1\u00bc","","","\1\u00bd","\1\u00be","\1\u00bf","\1\44\1\uffff\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",""};
static final short[] DFA7_eot=DFA.unpackEncodedString(DFA7_eotS);
static final short[] DFA7_eof=DFA.unpackEncodedString(DFA7_eofS);
static final char[] DFA7_min=DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
static final char[] DFA7_max=DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
static final short[] DFA7_accept=DFA.unpackEncodedString(DFA7_acceptS);
static final short[] DFA7_special=DFA.unpackEncodedString(DFA7_specialS);
static final short[][] DFA7_transition;
static {
int numStates=DFA7_transitionS.length;
DFA7_transition=new short[numStates][];
for (int i=0; i < numStates; i++) {
DFA7_transition[i]=DFA.unpackEncodedString(DFA7_transitionS[i]);
}
}
class DFA7 extends DFA {
public DFA7(BaseRecognizer recognizer){
this.recognizer=recognizer;
this.decisionNumber=7;
this.eot=DFA7_eot;
this.eof=DFA7_eof;
this.min=DFA7_min;
this.max=DFA7_max;
this.accept=DFA7_accept;
this.special=DFA7_special;
this.transition=DFA7_transition;
}
public String getDescription(){
return "1:1: Tokens : ( AT_FULL | AT_PURE | AT_IMMUTABLE | AT_SHARE | AT_UNIQUE | PUBLIC_BEHAVIOR | FULL | PURE | IMMUTABLE | SHARE | UNIQUE | NONE | LSBRACKET | RSBRACKET | PERM | EQUAL | EQUALOPERATOR | IN | THIS | RESULT | PARAM | REQUIRES | ENSURES | QUOTE | AND | USE | USEFIELDS | PUNCTUATION | CASES | LCBRACKET | RCBRACKET | CLASS_STATES | REFINE | VALUE | STATE | STATES | DIM | NAME | INV | OPERATOR | SEMICOLON | LESS | LESSTHANEQUAL | GREATER | GREATERTHANEQUAL | ANDD | OR | JMLSTART | JMLEND | PLUSMINUSOPERATOR | ASSIGNABLE | NOTHING | EVERYTHING | GHOST | INT | INVARIANT | OLD | ID | NUMBERS | WS );";
}
}
public static String getDFA7_specialS(){
Cloner cloner=new Cloner();
DFA7_specialS=cloner.deepClone(DFA7_specialS);
return DFA7_specialS;
}
public static String getDFA7_maxS(){
Cloner cloner=new Cloner();
DFA7_maxS=cloner.deepClone(DFA7_maxS);
return DFA7_maxS;
}
public static String getDFA7_acceptS(){
Cloner cloner=new Cloner();
DFA7_acceptS=cloner.deepClone(DFA7_acceptS);
return DFA7_acceptS;
}
public static String getDFA7_eotS(){
Cloner cloner=new Cloner();
DFA7_eotS=cloner.deepClone(DFA7_eotS);
return DFA7_eotS;
}
public DFA7 getDfa7(){
Cloner cloner=new Cloner();
dfa7=cloner.deepClone(dfa7);
return dfa7;
}
public static String getDFA7_minS(){
Cloner cloner=new Cloner();
DFA7_minS=cloner.deepClone(DFA7_minS);
return DFA7_minS;
}
public static String getDFA7_eofS(){
Cloner cloner=new Cloner();
DFA7_eofS=cloner.deepClone(DFA7_eofS);
return DFA7_eofS;
}
}
