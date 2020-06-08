package uma.SMC;

import org.antlr.runtime.*;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class PluralParser extends Parser {
    public static final String[] tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>", "<UP>", "AT_FULL", "AT_PURE", "AT_IMMUTABLE", "AT_SHARE", "AT_UNIQUE", "PUBLIC_BEHAVIOR", "FULL", "PURE", "IMMUTABLE", "SHARE", "UNIQUE", "NONE", "LSBRACKET", "RSBRACKET", "PERM", "EQUAL", "EQUALOPERATOR", "IN", "THIS", "RESULT", "PARAM", "REQUIRES", "ENSURES", "QUOTE", "AND", "USE", "USEFIELDS", "PUNCTUATION", "CASES", "LCBRACKET", "RCBRACKET", "CLASS_STATES", "REFINE", "VALUE", "STATE", "STATES", "DIM", "NAME", "INV", "OPERATOR", "SEMICOLON", "LESS", "LESSTHANEQUAL", "GREATER", "GREATERTHANEQUAL", "ANDD", "OR", "JMLSTART", "JMLEND", "PLUSMINUSOPERATOR", "ASSIGNABLE", "NOTHING", "EVERYTHING", "GHOST", "INT", "INVARIANT", "OLD", "ID", "NUMBERS", "WS"};
    public static final int PUNCTUATION = 31;
    public static final int CASES = 32;
    public static final int EVERYTHING = 56;
    public static final int EQUALOPERATOR = 20;
    public static final int PARAM = 24;
    public static final int IMMUTABLE = 12;
    public static final int JMLEND = 52;
    public static final int PUBLIC_BEHAVIOR = 9;
    public static final int ID = 61;
    public static final int AND = 28;
    public static final int EOF = -1;
    public static final int USEFIELDS = 30;
    public static final int STATES = 39;
    public static final int ENSURES = 26;
    public static final int QUOTE = 27;
    public static final int PURE = 11;
    public static final int AT_UNIQUE = 8;
    public static final int NAME = 41;
    public static final int GREATER = 47;
    public static final int FULL = 10;
    public static final int IN = 21;
    public static final int RSBRACKET = 17;
    public static final int EQUAL = 19;
    public static final int LCBRACKET = 33;
    public static final int LESS = 45;
    public static final int THIS = 22;
    public static final int REFINE = 36;
    public static final int NOTHING = 55;
    public static final int SHARE = 13;
    public static final int LESSTHANEQUAL = 46;
    public static final int AT_IMMUTABLE = 6;
    public static final int RCBRACKET = 34;
    public static final int CLASS_STATES = 35;
    public static final int AT_PURE = 5;
    public static final int NUMBERS = 62;
    public static final int ASSIGNABLE = 54;
    public static final int AT_FULL = 4;
    public static final int UNIQUE = 14;
    public static final int STATE = 38;
    public static final int GHOST = 57;
    public static final int OPERATOR = 43;
    public static final int INV = 42;
    public static final int INVARIANT = 59;
    public static final int OLD = 60;
    public static final int RESULT = 23;
    public static final int INT = 58;
    public static final int SEMICOLON = 44;
    public static final int JMLSTART = 51;
    public static final int VALUE = 37;
    public static final int PLUSMINUSOPERATOR = 53;
    public static final int LSBRACKET = 16;
    public static final int WS = 63;
    public static final int REQUIRES = 25;
    public static final int NONE = 15;
    public static final int OR = 50;
    public static final int DIM = 40;
    public static final int AT_SHARE = 7;
    public static final int USE = 29;
    public static final int ANDD = 49;
    public static final int GREATERTHANEQUAL = 48;
    public static final int PERM = 18;

    public PluralParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }

    public PluralParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() {
        return PluralParser.tokenNames;
    }

    public String getGrammarFileName() {
        return "/Users/ayeshasadiq/Documents/workspace/permission-specs/pt/uma/Plural/grammar/Plural.g";
    }

    public final void jmlSpecifications() throws RecognitionException {
        try {
            int alt1 = 2;
            int LA1_0 = input.LA(1);
            if ((LA1_0 == GHOST || LA1_0 == INVARIANT)) {
                alt1 = 1;
            } else if ((LA1_0 == JMLSTART)) {
                alt1 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 1, 0, input);
                throw nvae;
            }
            switch (alt1) {
                case 1: {
                    pushFollow(FOLLOW_jmlClassSpecifications_in_jmlSpecifications1071);
                    jmlClassSpecifications();
                    state._fsp--;
                }
                break;
                case 2: {
                    pushFollow(FOLLOW_jmlMethodSpecification_in_jmlSpecifications1073);
                    jmlMethodSpecification();
                    state._fsp--;
                }
                break;
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void jmlClassSpecifications() throws RecognitionException {
        try {
            int alt2 = 2;
            int LA2_0 = input.LA(1);
            if ((LA2_0 == GHOST)) {
                alt2 = 1;
            } else if ((LA2_0 == INVARIANT)) {
                alt2 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 2, 0, input);
                throw nvae;
            }
            switch (alt2) {
                case 1: {
                    pushFollow(FOLLOW_jmlGhostDeclaration_in_jmlClassSpecifications1081);
                    jmlGhostDeclaration();
                    state._fsp--;
                }
                break;
                case 2: {
                    pushFollow(FOLLOW_jmlGhostInv_in_jmlClassSpecifications1083);
                    jmlGhostInv();
                    state._fsp--;
                }
                break;
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void jmlGhostDeclaration() throws RecognitionException {
        Token dim = null;
        try {
            {
                match(input, GHOST, FOLLOW_GHOST_in_jmlGhostDeclaration1090);
                match(input, INT, FOLLOW_INT_in_jmlGhostDeclaration1092);
                dim = (Token) match(input, ID, FOLLOW_ID_in_jmlGhostDeclaration1096);
                match(input, SEMICOLON, FOLLOW_SEMICOLON_in_jmlGhostDeclaration1098);
                EJmlSpecification.setDimensionName((dim != null ? dim.getText() : null));
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void jmlGhostInv() throws RecognitionException {
        Token low = null;
        Token high = null;
        try {
            {
                match(input, INVARIANT, FOLLOW_INVARIANT_in_jmlGhostInv1106);
                low = (Token) match(input, NUMBERS, FOLLOW_NUMBERS_in_jmlGhostInv1111);
                match(input, LESSTHANEQUAL, FOLLOW_LESSTHANEQUAL_in_jmlGhostInv1113);
                match(input, ID, FOLLOW_ID_in_jmlGhostInv1115);
                match(input, ANDD, FOLLOW_ANDD_in_jmlGhostInv1117);
                match(input, ID, FOLLOW_ID_in_jmlGhostInv1119);
                match(input, LESSTHANEQUAL, FOLLOW_LESSTHANEQUAL_in_jmlGhostInv1121);
                high = (Token) match(input, NUMBERS, FOLLOW_NUMBERS_in_jmlGhostInv1125);
                match(input, SEMICOLON, FOLLOW_SEMICOLON_in_jmlGhostInv1127);
                int nlow = Integer.parseInt((low != null ? low.getText() : null));
                int nhigh = Integer.parseInt((high != null ? high.getText() : null));
                EJmlSpecification.setDimensionValues(nlow, nhigh);
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void jmlMethodSpecification() throws RecognitionException {
        try {
            {
                match(input, JMLSTART, FOLLOW_JMLSTART_in_jmlMethodSpecification1136);
                match(input, PUBLIC_BEHAVIOR, FOLLOW_PUBLIC_BEHAVIOR_in_jmlMethodSpecification1138);
                int alt3 = 2;
                int LA3_0 = input.LA(1);
                if ((LA3_0 == REQUIRES)) {
                    alt3 = 1;
                }
                switch (alt3) {
                    case 1: {
                        match(input, REQUIRES, FOLLOW_REQUIRES_in_jmlMethodSpecification1142);
                        pushFollow(FOLLOW_jmlRequires_in_jmlMethodSpecification1144);
                        jmlRequires();
                        state._fsp--;
                        match(input, SEMICOLON, FOLLOW_SEMICOLON_in_jmlMethodSpecification1147);
                    }
                    break;
                }
                int alt4 = 2;
                int LA4_0 = input.LA(1);
                if ((LA4_0 == ASSIGNABLE)) {
                    alt4 = 1;
                }
                switch (alt4) {
                    case 1: {
                        pushFollow(FOLLOW_jmlAssign_in_jmlMethodSpecification1152);
                        jmlAssign();
                        state._fsp--;
                    }
                    break;
                }
                int alt5 = 2;
                int LA5_0 = input.LA(1);
                if ((LA5_0 == ENSURES)) {
                    alt5 = 1;
                }
                switch (alt5) {
                    case 1: {
                        pushFollow(FOLLOW_jmlEnsures_in_jmlMethodSpecification1157);
                        jmlEnsures();
                        state._fsp--;
                    }
                    break;
                }
                match(input, JMLEND, FOLLOW_JMLEND_in_jmlMethodSpecification1161);
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void jmlRequires() throws RecognitionException {
        try {
            int alt6 = 3;
            int LA6_0 = input.LA(1);
            if ((LA6_0 == ID)) {
                int LA6_1 = input.LA(2);
                if ((LA6_1 == EQUALOPERATOR)) {
                    int LA6_2 = input.LA(3);
                    if ((LA6_2 == NUMBERS)) {
                        int LA6_4 = input.LA(4);
                        if ((LA6_4 == OR)) {
                            alt6 = 2;
                        } else if ((LA6_4 == SEMICOLON)) {
                            alt6 = 1;
                        } else {
                            NoViableAltException nvae = new NoViableAltException("", 6, 4, input);
                            throw nvae;
                        }
                    } else {
                        NoViableAltException nvae = new NoViableAltException("", 6, 2, input);
                        throw nvae;
                    }
                } else if ((LA6_1 == LESSTHANEQUAL)) {
                    alt6 = 3;
                } else {
                    NoViableAltException nvae = new NoViableAltException("", 6, 1, input);
                    throw nvae;
                }
            } else {
                NoViableAltException nvae = new NoViableAltException("", 6, 0, input);
                throw nvae;
            }
            switch (alt6) {
                case 1: {
                    pushFollow(FOLLOW_jmlReq_in_jmlRequires1167);
                    jmlReq();
                    state._fsp--;
                }
                break;
                case 2: {
                    pushFollow(FOLLOW_jmlOrReq_in_jmlRequires1169);
                    jmlOrReq();
                    state._fsp--;
                }
                break;
                case 3: {
                    pushFollow(FOLLOW_jmlLessThanEqualReq_in_jmlRequires1171);
                    jmlLessThanEqualReq();
                    state._fsp--;
                }
                break;
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void jmlOrReq() throws RecognitionException {
        try {
            {
                pushFollow(FOLLOW_jmlReq_in_jmlOrReq1178);
                jmlReq();
                state._fsp--;
                int cnt7 = 0;
                loop7:
                do {
                    int alt7 = 2;
                    int LA7_0 = input.LA(1);
                    if ((LA7_0 == OR)) {
                        alt7 = 1;
                    }
                    switch (alt7) {
                        case 1: {
                            match(input, OR, FOLLOW_OR_in_jmlOrReq1181);
                            pushFollow(FOLLOW_jmlReq_in_jmlOrReq1183);
                            jmlReq();
                            state._fsp--;
                        }
                        break;
                        default:
                            if (cnt7 >= 1) break loop7;
                            EarlyExitException eee = new EarlyExitException(7, input);
                            throw eee;
                    }
                    cnt7++;
                }
                while (true);
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void jmlLessThanEqualReq() throws RecognitionException {
        Token tstate = null;
        try {
            {
                match(input, ID, FOLLOW_ID_in_jmlLessThanEqualReq1192);
                match(input, LESSTHANEQUAL, FOLLOW_LESSTHANEQUAL_in_jmlLessThanEqualReq1194);
                tstate = (Token) match(input, NUMBERS, FOLLOW_NUMBERS_in_jmlLessThanEqualReq1198);
                int n = Integer.parseInt((tstate != null ? tstate.getText() : null));
                int x = 1;
                while (x <= n) {
                    EJmlSpecification.addRequires("" + x);
                    x++;
                }
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void jmlReq() throws RecognitionException {
        Token strState = null;
        try {
            {
                match(input, ID, FOLLOW_ID_in_jmlReq1207);
                match(input, EQUALOPERATOR, FOLLOW_EQUALOPERATOR_in_jmlReq1209);
                strState = (Token) match(input, NUMBERS, FOLLOW_NUMBERS_in_jmlReq1213);
                EJmlSpecification.addRequires((strState != null ? strState.getText() : null));
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void jmlEnsures() throws RecognitionException {
        try {
            int alt8 = 2;
            int LA8_0 = input.LA(1);
            if ((LA8_0 == ENSURES)) {
                int LA8_1 = input.LA(2);
                if ((LA8_1 == ID)) {
                    int LA8_2 = input.LA(3);
                    if ((LA8_2 == EQUALOPERATOR)) {
                        int LA8_3 = input.LA(4);
                        if ((LA8_3 == NUMBERS)) {
                            alt8 = 1;
                        } else if ((LA8_3 == OLD)) {
                            alt8 = 2;
                        } else {
                            NoViableAltException nvae = new NoViableAltException("", 8, 3, input);
                            throw nvae;
                        }
                    } else {
                        NoViableAltException nvae = new NoViableAltException("", 8, 2, input);
                        throw nvae;
                    }
                } else {
                    NoViableAltException nvae = new NoViableAltException("", 8, 1, input);
                    throw nvae;
                }
            } else {
                NoViableAltException nvae = new NoViableAltException("", 8, 0, input);
                throw nvae;
            }
            switch (alt8) {
                case 1: {
                    pushFollow(FOLLOW_jmlEns_in_jmlEnsures1221);
                    jmlEns();
                    state._fsp--;
                }
                break;
                case 2: {
                    pushFollow(FOLLOW_jmlOldEns_in_jmlEnsures1223);
                    jmlOldEns();
                    state._fsp--;
                }
                break;
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void jmlOldEns() throws RecognitionException {
        Token ope = null;
        Token num = null;
        try {
            {
                match(input, ENSURES, FOLLOW_ENSURES_in_jmlOldEns1229);
                match(input, ID, FOLLOW_ID_in_jmlOldEns1231);
                match(input, EQUALOPERATOR, FOLLOW_EQUALOPERATOR_in_jmlOldEns1233);
                match(input, OLD, FOLLOW_OLD_in_jmlOldEns1235);
                match(input, LSBRACKET, FOLLOW_LSBRACKET_in_jmlOldEns1237);
                match(input, ID, FOLLOW_ID_in_jmlOldEns1239);
                match(input, RSBRACKET, FOLLOW_RSBRACKET_in_jmlOldEns1241);
                int alt9 = 2;
                int LA9_0 = input.LA(1);
                if ((LA9_0 == PLUSMINUSOPERATOR)) {
                    alt9 = 1;
                }
                switch (alt9) {
                    case 1: {
                        ope = (Token) match(input, PLUSMINUSOPERATOR, FOLLOW_PLUSMINUSOPERATOR_in_jmlOldEns1246);
                        num = (Token) match(input, NUMBERS, FOLLOW_NUMBERS_in_jmlOldEns1250);
                    }
                    break;
                }
                match(input, SEMICOLON, FOLLOW_SEMICOLON_in_jmlOldEns1254);
                if ((ope != null ? ope.getText() : null) != null && (num != null ? num.getText() : null) != null)
                    EJmlSpecification.setEnsures("old" + (ope != null ? ope.getText() : null) + (num != null ? num.getText() : null));
                else EJmlSpecification.setEnsures("old");
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void jmlEns() throws RecognitionException {
        Token strState = null;
        try {
            {
                match(input, ENSURES, FOLLOW_ENSURES_in_jmlEns1263);
                match(input, ID, FOLLOW_ID_in_jmlEns1265);
                match(input, EQUALOPERATOR, FOLLOW_EQUALOPERATOR_in_jmlEns1267);
                strState = (Token) match(input, NUMBERS, FOLLOW_NUMBERS_in_jmlEns1271);
                match(input, SEMICOLON, FOLLOW_SEMICOLON_in_jmlEns1273);
                EJmlSpecification.setEnsures((strState != null ? strState.getText() : null));
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void jmlAssign() throws RecognitionException {
        Token strPerm = null;
        try {
            {
                match(input, ASSIGNABLE, FOLLOW_ASSIGNABLE_in_jmlAssign1280);
                int alt10 = 3;
                switch (input.LA(1)) {
                    case EVERYTHING: {
                        alt10 = 1;
                    }
                    break;
                    case NOTHING: {
                        alt10 = 2;
                    }
                    break;
                    case ID: {
                        alt10 = 3;
                    }
                    break;
                    default:
                        NoViableAltException nvae = new NoViableAltException("", 10, 0, input);
                        throw nvae;
                }
                switch (alt10) {
                    case 1: {
                        strPerm = (Token) match(input, EVERYTHING, FOLLOW_EVERYTHING_in_jmlAssign1285);
                    }
                    break;
                    case 2: {
                        strPerm = (Token) match(input, NOTHING, FOLLOW_NOTHING_in_jmlAssign1289);
                    }
                    break;
                    case 3: {
                        strPerm = (Token) match(input, ID, FOLLOW_ID_in_jmlAssign1293);
                    }
                    break;
                }
                match(input, SEMICOLON, FOLLOW_SEMICOLON_in_jmlAssign1296);
                String perm = "Pure";
                String str = (strPerm != null ? strPerm.getText() : null);
                if (str.compareTo("\\nothing") == 0) perm = "Pure";
                else if (str.length() > 0) perm = "Full";
                EJmlSpecification.setPerm(perm);
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void specifications() throws RecognitionException {
        try {
            int alt11 = 4;
            switch (input.LA(1)) {
                case AT_FULL:
                case AT_PURE:
                case AT_IMMUTABLE:
                case AT_SHARE:
                case AT_UNIQUE:
                case PERM: {
                    alt11 = 1;
                }
                break;
                case CASES: {
                    alt11 = 2;
                }
                break;
                case CLASS_STATES: {
                    alt11 = 3;
                }
                break;
                case REFINE: {
                    alt11 = 4;
                }
                break;
                default:
                    NoViableAltException nvae = new NoViableAltException("", 11, 0, input);
                    throw nvae;
            }
            switch (alt11) {
                case 1: {
                    pushFollow(FOLLOW_perm_in_specifications1309);
                    perm();
                    state._fsp--;
                }
                break;
                case 2: {
                    pushFollow(FOLLOW_cases_in_specifications1311);
                    cases();
                    state._fsp--;
                }
                break;
                case 3: {
                    pushFollow(FOLLOW_classstates_in_specifications1313);
                    classstates();
                    state._fsp--;
                }
                break;
                case 4: {
                    pushFollow(FOLLOW_refine_in_specifications1315);
                    refine();
                    state._fsp--;
                }
                break;
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void refine() throws RecognitionException {
        try {
            {
                match(input, REFINE, FOLLOW_REFINE_in_refine1322);
                match(input, LSBRACKET, FOLLOW_LSBRACKET_in_refine1324);
                match(input, LCBRACKET, FOLLOW_LCBRACKET_in_refine1326);
                {
                    pushFollow(FOLLOW_states_in_refine1329);
                    states();
                    state._fsp--;
                }
                loop12:
                do {
                    int alt12 = 2;
                    int LA12_0 = input.LA(1);
                    if ((LA12_0 == PUNCTUATION)) {
                        alt12 = 1;
                    }
                    switch (alt12) {
                        case 1: {
                            match(input, PUNCTUATION, FOLLOW_PUNCTUATION_in_refine1333);
                            pushFollow(FOLLOW_states_in_refine1335);
                            states();
                            state._fsp--;
                        }
                        break;
                        default:
                            break loop12;
                    }
                }
                while (true);
                match(input, RCBRACKET, FOLLOW_RCBRACKET_in_refine1340);
                match(input, RSBRACKET, FOLLOW_RSBRACKET_in_refine1342);
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void states() throws RecognitionException {
        try {
            {
                match(input, STATES, FOLLOW_STATES_in_states1351);
                match(input, LSBRACKET, FOLLOW_LSBRACKET_in_states1353);
                pushFollow(FOLLOW_dimension_in_states1355);
                dimension();
                state._fsp--;
                match(input, PUNCTUATION, FOLLOW_PUNCTUATION_in_states1357);
                loop13:
                do {
                    int alt13 = 2;
                    int LA13_0 = input.LA(1);
                    if ((LA13_0 == VALUE)) {
                        alt13 = 1;
                    }
                    switch (alt13) {
                        case 1: {
                            pushFollow(FOLLOW_value_in_states1360);
                            value();
                            state._fsp--;
                        }
                        break;
                        default:
                            break loop13;
                    }
                }
                while (true);
                match(input, RSBRACKET, FOLLOW_RSBRACKET_in_states1364);
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void dimension() throws RecognitionException {
        Token dim = null;
        try {
            {
                match(input, DIM, FOLLOW_DIM_in_dimension1373);
                match(input, EQUAL, FOLLOW_EQUAL_in_dimension1375);
                match(input, QUOTE, FOLLOW_QUOTE_in_dimension1377);
                dim = (Token) match(input, ID, FOLLOW_ID_in_dimension1381);
                match(input, QUOTE, FOLLOW_QUOTE_in_dimension1383);
                EVMDDSMCGenerator.addDimension((dim != null ? dim.getText() : null));
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void value() throws RecognitionException {
        try {
            {
                match(input, VALUE, FOLLOW_VALUE_in_value1393);
                match(input, EQUAL, FOLLOW_EQUAL_in_value1395);
                match(input, LCBRACKET, FOLLOW_LCBRACKET_in_value1397);
                pushFollow(FOLLOW_item_in_value1399);
                item();
                state._fsp--;
                loop14:
                do {
                    int alt14 = 2;
                    int LA14_0 = input.LA(1);
                    if ((LA14_0 == PUNCTUATION)) {
                        alt14 = 1;
                    }
                    switch (alt14) {
                        case 1: {
                            match(input, PUNCTUATION, FOLLOW_PUNCTUATION_in_value1402);
                            pushFollow(FOLLOW_item_in_value1404);
                            item();
                            state._fsp--;
                        }
                        break;
                        default:
                            break loop14;
                    }
                }
                while (true);
                match(input, RCBRACKET, FOLLOW_RCBRACKET_in_value1408);
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void item() throws RecognitionException {
        Token state_name = null;
        try {
            {
                match(input, QUOTE, FOLLOW_QUOTE_in_item1415);
                state_name = (Token) match(input, ID, FOLLOW_ID_in_item1419);
                match(input, QUOTE, FOLLOW_QUOTE_in_item1421);
                EVMDDSMCGenerator.addDimensionValue((state_name != null ? state_name.getText() : null));
                EVMDDSMCGenerator.addState((state_name != null ? state_name.getText() : null));
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void classstates() throws RecognitionException {
        try {
            {
                pushFollow(FOLLOW_start_classstates_in_classstates1432);
                startClassstates();
                state._fsp--;
                pushFollow(FOLLOW_state_in_classstates1434);
                state();
                state._fsp--;
                loop15:
                do {
                    int alt15 = 2;
                    int LA15_0 = input.LA(1);
                    if ((LA15_0 == PUNCTUATION)) {
                        alt15 = 1;
                    }
                    switch (alt15) {
                        case 1: {
                            match(input, PUNCTUATION, FOLLOW_PUNCTUATION_in_classstates1437);
                            pushFollow(FOLLOW_state_in_classstates1439);
                            state();
                            state._fsp--;
                        }
                        break;
                        default:
                            break loop15;
                    }
                }
                while (true);
                pushFollow(FOLLOW_end_classstates_in_classstates1443);
                endclassstates();
                state._fsp--;
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void startClassstates() throws RecognitionException {
        try {
            {
                match(input, CLASS_STATES, FOLLOW_CLASS_STATES_in_start_classstates1450);
                match(input, LSBRACKET, FOLLOW_LSBRACKET_in_start_classstates1452);
                match(input, LCBRACKET, FOLLOW_LCBRACKET_in_start_classstates1454);
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void endclassstates() throws RecognitionException {
        try {
            {
                match(input, RCBRACKET, FOLLOW_RCBRACKET_in_end_classstates1461);
                match(input, RSBRACKET, FOLLOW_RSBRACKET_in_end_classstates1463);
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void state() throws RecognitionException {
        Token state_name = null;
        try {
            {
                match(input, STATE, FOLLOW_STATE_in_state1471);
                match(input, LSBRACKET, FOLLOW_LSBRACKET_in_state1473);
                match(input, NAME, FOLLOW_NAME_in_state1475);
                match(input, EQUAL, FOLLOW_EQUAL_in_state1477);
                match(input, QUOTE, FOLLOW_QUOTE_in_state1479);
                state_name = (Token) match(input, ID, FOLLOW_ID_in_state1483);
                match(input, QUOTE, FOLLOW_QUOTE_in_state1485);
                EVMDDSMCGenerator.addState((state_name != null ? state_name.getText() : null));
                loop16:
                do {
                    int alt16 = 2;
                    int LA16_0 = input.LA(1);
                    if ((LA16_0 == PUNCTUATION)) {
                        alt16 = 1;
                    }
                    switch (alt16) {
                        case 1: {
                            match(input, PUNCTUATION, FOLLOW_PUNCTUATION_in_state1490);
                            pushFollow(FOLLOW_invariant_in_state1492);
                            invariant();
                            state._fsp--;
                        }
                        break;
                        default:
                            break loop16;
                    }
                }
                while (true);
                match(input, RSBRACKET, FOLLOW_RSBRACKET_in_state1496);
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void invariant() throws RecognitionException {
        try {
            {
                match(input, INV, FOLLOW_INV_in_invariant1591);
                match(input, EQUAL, FOLLOW_EQUAL_in_invariant1593);
                match(input, QUOTE, FOLLOW_QUOTE_in_invariant1595);
                int alt18 = 2;
                int LA18_0 = input.LA(1);
                if (((LA18_0 >= FULL && LA18_0 <= NONE) || LA18_0 == ID)) {
                    alt18 = 1;
                }
                switch (alt18) {
                    case 1: {
                        pushFollow(FOLLOW_condition_in_invariant1598);
                        condition();
                        state._fsp--;
                        loop17:
                        do {
                            int alt17 = 2;
                            int LA17_0 = input.LA(1);
                            if ((LA17_0 == AND)) {
                                alt17 = 1;
                            }
                            switch (alt17) {
                                case 1: {
                                    match(input, AND, FOLLOW_AND_in_invariant1601);
                                    pushFollow(FOLLOW_condition_in_invariant1603);
                                    condition();
                                    state._fsp--;
                                }
                                break;
                                default:
                                    break loop17;
                            }
                        }
                        while (true);
                    }
                    break;
                }
                match(input, QUOTE, FOLLOW_QUOTE_in_invariant1609);
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void condition() throws RecognitionException {
        Token var = null;
        Token op = null;
        Token val = null;
        Token st = null;
        PluralParser.AccesspermissionReturn ap = null;
        try {
            int alt20 = 2;
            int LA20_0 = input.LA(1);
            if ((LA20_0 == ID)) {
                alt20 = 1;
            } else if (((LA20_0 >= FULL && LA20_0 <= NONE))) {
                alt20 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 20, 0, input);
                throw nvae;
            }
            switch (alt20) {
                case 1: {
                    var = (Token) match(input, ID, FOLLOW_ID_in_condition1620);
                    op = (Token) match(input, OPERATOR, FOLLOW_OPERATOR_in_condition1624);
                    val = (Token) match(input, ID, FOLLOW_ID_in_condition1628);
                    String variable = (var != null ? var.getText() : null);
                    String opertor = (op != null ? op.getText() : null);
                    String value = (val != null ? val.getText() : null);
                    EVMDDSMCGenerator.addBoolStateInvariant(variable, opertor, value);
                }
                break;
                case 2: {
                    pushFollow(FOLLOW_accesspermission_in_condition1646);
                    ap = accesspermission();
                    state._fsp--;
                    match(input, LSBRACKET, FOLLOW_LSBRACKET_in_condition1648);
                    var = (Token) input.LT(1);
                    if (input.LA(1) == THIS || input.LA(1) == ID) {
                        input.consume();
                        state.errorRecovery = false;
                    } else {
                        MismatchedSetException mse = new MismatchedSetException(null, input);
                        throw mse;
                    }
                    match(input, RSBRACKET, FOLLOW_RSBRACKET_in_condition1658);
                    int alt19 = 2;
                    int LA19_0 = input.LA(1);
                    if ((LA19_0 == IN)) {
                        alt19 = 1;
                    }
                    switch (alt19) {
                        case 1: {
                            match(input, IN, FOLLOW_IN_in_condition1661);
                            st = (Token) match(input, ID, FOLLOW_ID_in_condition1665);
                        }
                        break;
                    }
                    String accessPermission = (ap != null ? input.toString(ap.getStart(), ap.getStop()) : null);
                    String variable = (var != null ? var.getText() : null);
                    String state = (st != null ? st.getText() : null);
                    if (state == null) state = "alive";
                    EVMDDSMCGenerator.addStateInvariant(accessPermission, variable, state);
                }
                break;
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void cases() throws RecognitionException {
        try {
            {
                match(input, CASES, FOLLOW_CASES_in_cases1676);
                match(input, LSBRACKET, FOLLOW_LSBRACKET_in_cases1678);
                match(input, LCBRACKET, FOLLOW_LCBRACKET_in_cases1680);
                pushFollow(FOLLOW_perm_in_cases1682);
                perm();
                state._fsp--;
                loop21:
                do {
                    int alt21 = 2;
                    int LA21_0 = input.LA(1);
                    if ((LA21_0 == PUNCTUATION)) {
                        alt21 = 1;
                    }
                    switch (alt21) {
                        case 1: {
                            pushFollow(FOLLOW_other_in_cases1685);
                            other();
                            state._fsp--;
                            pushFollow(FOLLOW_perm_in_cases1687);
                            perm();
                            state._fsp--;
                        }
                        break;
                        default:
                            break loop21;
                    }
                }
                while (true);
                match(input, RCBRACKET, FOLLOW_RCBRACKET_in_cases1691);
                match(input, RSBRACKET, FOLLOW_RSBRACKET_in_cases1693);
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void other() throws RecognitionException {
        try {
            {
                match(input, PUNCTUATION, FOLLOW_PUNCTUATION_in_other1701);
                EVMDDSMCGenerator.addCase();
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void perm() throws RecognitionException {
        try {
            int alt22 = 2;
            int LA22_0 = input.LA(1);
            if ((LA22_0 == PERM)) {
                alt22 = 1;
            } else if (((LA22_0 >= AT_FULL && LA22_0 <= AT_UNIQUE))) {
                alt22 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 22, 0, input);
                throw nvae;
            }
            switch (alt22) {
                case 1: {
                    match(input, PERM, FOLLOW_PERM_in_perm1712);
                    match(input, LSBRACKET, FOLLOW_LSBRACKET_in_perm1714);
                    pushFollow(FOLLOW_requires_ensures_clause_in_perm1716);
                    requiresensuresClause();
                    state._fsp--;
                    match(input, RSBRACKET, FOLLOW_RSBRACKET_in_perm1718);
                }
                break;
                case 2: {
                    pushFollow(FOLLOW_attype_in_perm1727);
                    attype();
                    state._fsp--;
                }
                break;
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void attype() throws RecognitionException {
        PluralParser.AtApPermissionReturn ap = null;
        PluralParser.TypestateReturn restate = null;
        PluralParser.TypestateReturn enstate = null;
        try {
            {
                pushFollow(FOLLOW_at_ap_permission_in_attype1735);
                ap = atappermission();
                state._fsp--;
                int alt27 = 2;
                int LA27_0 = input.LA(1);
                if ((LA27_0 == LSBRACKET)) {
                    alt27 = 1;
                }
                switch (alt27) {
                    case 1: {
                        match(input, LSBRACKET, FOLLOW_LSBRACKET_in_attype1738);
                        int alt23 = 2;
                        int LA23_0 = input.LA(1);
                        if ((LA23_0 == REQUIRES)) {
                            alt23 = 1;
                        }
                        switch (alt23) {
                            case 1: {
                                match(input, REQUIRES, FOLLOW_REQUIRES_in_attype1741);
                                match(input, EQUAL, FOLLOW_EQUAL_in_attype1743);
                                match(input, QUOTE, FOLLOW_QUOTE_in_attype1745);
                                pushFollow(FOLLOW_typestate_in_attype1749);
                                restate = typestate();
                                state._fsp--;
                                match(input, QUOTE, FOLLOW_QUOTE_in_attype1751);
                            }
                            break;
                        }
                        int alt24 = 2;
                        int LA24_0 = input.LA(1);
                        if ((LA24_0 == PUNCTUATION)) {
                            int LA24_1 = input.LA(2);
                            if ((LA24_1 == EOF || LA24_1 == ENSURES || LA24_1 == PUNCTUATION || LA24_1 == RCBRACKET)) {
                                alt24 = 1;
                            }
                        }
                        switch (alt24) {
                            case 1: {
                                match(input, PUNCTUATION, FOLLOW_PUNCTUATION_in_attype1756);
                            }
                            break;
                        }
                        int alt25 = 2;
                        int LA25_0 = input.LA(1);
                        if ((LA25_0 == ENSURES)) {
                            alt25 = 1;
                        }
                        switch (alt25) {
                            case 1: {
                                match(input, ENSURES, FOLLOW_ENSURES_in_attype1761);
                                match(input, EQUAL, FOLLOW_EQUAL_in_attype1763);
                                match(input, QUOTE, FOLLOW_QUOTE_in_attype1765);
                                pushFollow(FOLLOW_typestate_in_attype1769);
                                enstate = typestate();
                                state._fsp--;
                                match(input, QUOTE, FOLLOW_QUOTE_in_attype1771);
                            }
                            break;
                        }
                        int alt26 = 2;
                        int LA26_0 = input.LA(1);
                        if ((LA26_0 == PUNCTUATION)) {
                            int LA26_1 = input.LA(2);
                            if ((LA26_1 == USE || LA26_1 == VALUE)) {
                                alt26 = 1;
                            }
                        }
                        switch (alt26) {
                            case 1: {
                                match(input, PUNCTUATION, FOLLOW_PUNCTUATION_in_attype1776);
                                pushFollow(FOLLOW_usevalue_in_attype1778);
                                usevalue();
                                state._fsp--;
                            }
                            break;
                        }
                    }
                    break;
                }
                String str = (ap != null ? input.toString(ap.getStart(), ap.getStop()) : null);
                str = str.substring(1);
                String re_state = (restate != null ? input.toString(restate.getStart(), restate.getStop()) : null);
                if (re_state == null) re_state = "alive";
                String en_state = (enstate != null ? input.toString(enstate.getStart(), enstate.getStop()) : null);
                if (en_state == null) en_state = "alive";
                EVMDDSMCGenerator.addRequiresAPTS(str.toString(), re_state);
                EVMDDSMCGenerator.addEnsuresAPTS(str.toString(), en_state);
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void usevalue() throws RecognitionException {
        try {
            int alt28 = 2;
            int LA28_0 = input.LA(1);
            if ((LA28_0 == USE)) {
                alt28 = 1;
            } else if ((LA28_0 == VALUE)) {
                alt28 = 2;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 28, 0, input);
                throw nvae;
            }
            switch (alt28) {
                case 1: {
                    match(input, USE, FOLLOW_USE_in_usevalue1792);
                    match(input, EQUAL, FOLLOW_EQUAL_in_usevalue1794);
                    match(input, USEFIELDS, FOLLOW_USEFIELDS_in_usevalue1796);
                }
                break;
                case 2: {
                    match(input, VALUE, FOLLOW_VALUE_in_usevalue1809);
                    match(input, EQUAL, FOLLOW_EQUAL_in_usevalue1811);
                    match(input, QUOTE, FOLLOW_QUOTE_in_usevalue1813);
                    match(input, ID, FOLLOW_ID_in_usevalue1815);
                    match(input, QUOTE, FOLLOW_QUOTE_in_usevalue1817);
                }
                break;
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void requiresensuresClause() throws RecognitionException {
        try {
            {
                int alt29 = 2;
                int LA29_0 = input.LA(1);
                if ((LA29_0 == REQUIRES)) {
                    alt29 = 1;
                }
                switch (alt29) {
                    case 1: {
                        pushFollow(FOLLOW_requires_clause_in_requires_ensures_clause1831);
                        requiresClause();
                        state._fsp--;
                    }
                    break;
                }
                int alt30 = 2;
                int LA30_0 = input.LA(1);
                if ((LA30_0 == PUNCTUATION)) {
                    alt30 = 1;
                }
                switch (alt30) {
                    case 1: {
                        match(input, PUNCTUATION, FOLLOW_PUNCTUATION_in_requires_ensures_clause1836);
                    }
                    break;
                }
                int alt31 = 2;
                int LA31_0 = input.LA(1);
                if ((LA31_0 == ENSURES)) {
                    alt31 = 1;
                }
                switch (alt31) {
                    case 1: {
                        pushFollow(FOLLOW_ensures_clause_in_requires_ensures_clause1841);
                        ensuresclause();
                        state._fsp--;
                    }
                    break;
                }
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void requiresClause() throws RecognitionException {
        try {
            {
                match(input, REQUIRES, FOLLOW_REQUIRES_in_requires_clause1852);
                match(input, EQUAL, FOLLOW_EQUAL_in_requires_clause1854);
                match(input, QUOTE, FOLLOW_QUOTE_in_requires_clause1856);
                pushFollow(FOLLOW_re_accesspermission_typestates_in_requires_clause1858);
                reaccesspermissionTypestates();
                state._fsp--;
                loop32:
                do {
                    int alt32 = 2;
                    int LA32_0 = input.LA(1);
                    if ((LA32_0 == AND)) {
                        alt32 = 1;
                    }
                    switch (alt32) {
                        case 1: {
                            match(input, AND, FOLLOW_AND_in_requires_clause1861);
                            pushFollow(FOLLOW_re_accesspermission_typestates_in_requires_clause1863);
                            reaccesspermissionTypestates();
                            state._fsp--;
                        }
                        break;
                        default:
                            break loop32;
                    }
                }
                while (true);
                match(input, QUOTE, FOLLOW_QUOTE_in_requires_clause1867);
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void ensuresclause() throws RecognitionException {
        try {
            {
                match(input, ENSURES, FOLLOW_ENSURES_in_ensures_clause1875);
                match(input, EQUAL, FOLLOW_EQUAL_in_ensures_clause1877);
                match(input, QUOTE, FOLLOW_QUOTE_in_ensures_clause1879);
                pushFollow(FOLLOW_en_accesspermission_typestates_in_ensures_clause1881);
                enaccesspermissiontypestates();
                state._fsp--;
                loop33:
                do {
                    int alt33 = 2;
                    int LA33_0 = input.LA(1);
                    if ((LA33_0 == AND)) {
                        alt33 = 1;
                    }
                    switch (alt33) {
                        case 1: {
                            match(input, AND, FOLLOW_AND_in_ensures_clause1884);
                            pushFollow(FOLLOW_en_accesspermission_typestates_in_ensures_clause1886);
                            enaccesspermissiontypestates();
                            state._fsp--;
                        }
                        break;
                        default:
                            break loop33;
                    }
                }
                while (true);
                match(input, QUOTE, FOLLOW_QUOTE_in_ensures_clause1890);
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void reaccesspermissionTypestates() throws RecognitionException {
        Token para = null;
        PluralParser.AccesspermissionReturn ap = null;
        PluralParser.TypestateReturn st = null;
        try {
            int alt36 = 3;
            int LA36_0 = input.LA(1);
            if (((LA36_0 >= FULL && LA36_0 <= NONE))) {
                int LA36_1 = input.LA(2);
                if ((LA36_1 == LSBRACKET)) {
                    int LA36_3 = input.LA(3);
                    if ((LA36_3 == PARAM)) {
                        alt36 = 2;
                    } else if ((LA36_3 == THIS)) {
                        alt36 = 1;
                    } else {
                        NoViableAltException nvae = new NoViableAltException("", 36, 3, input);
                        throw nvae;
                    }
                } else {
                    NoViableAltException nvae = new NoViableAltException("", 36, 1, input);
                    throw nvae;
                }
            } else if ((LA36_0 == PARAM)) {
                alt36 = 3;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 36, 0, input);
                throw nvae;
            }
            switch (alt36) {
                case 1: {
                    pushFollow(FOLLOW_accesspermission_in_re_accesspermission_typestates1901);
                    ap = accesspermission();
                    state._fsp--;
                    match(input, LSBRACKET, FOLLOW_LSBRACKET_in_re_accesspermission_typestates1903);
                    match(input, THIS, FOLLOW_THIS_in_re_accesspermission_typestates1905);
                    match(input, RSBRACKET, FOLLOW_RSBRACKET_in_re_accesspermission_typestates1907);
                    int alt34 = 2;
                    int LA34_0 = input.LA(1);
                    if ((LA34_0 == IN)) {
                        alt34 = 1;
                    }
                    switch (alt34) {
                        case 1: {
                            match(input, IN, FOLLOW_IN_in_re_accesspermission_typestates1910);
                            pushFollow(FOLLOW_typestate_in_re_accesspermission_typestates1914);
                            st = typestate();
                            state._fsp--;
                        }
                        break;
                    }
                    String en_state = (st != null ? input.toString(st.getStart(), st.getStop()) : null);
                    if (en_state == null) en_state = "alive";
                    EVMDDSMCGenerator.addRequiresAPTS((ap != null ? input.toString(ap.getStart(), ap.getStop()) : null), en_state);
                }
                break;
                case 2: {
                    pushFollow(FOLLOW_accesspermission_in_re_accesspermission_typestates1954);
                    ap = accesspermission();
                    state._fsp--;
                    match(input, LSBRACKET, FOLLOW_LSBRACKET_in_re_accesspermission_typestates1956);
                    para = (Token) match(input, PARAM, FOLLOW_PARAM_in_re_accesspermission_typestates1960);
                    match(input, RSBRACKET, FOLLOW_RSBRACKET_in_re_accesspermission_typestates1962);
                    int alt35 = 2;
                    int LA35_0 = input.LA(1);
                    if ((LA35_0 == IN)) {
                        alt35 = 1;
                    }
                    switch (alt35) {
                        case 1: {
                            match(input, IN, FOLLOW_IN_in_re_accesspermission_typestates1965);
                            pushFollow(FOLLOW_typestate_in_re_accesspermission_typestates1969);
                            st = typestate();
                            state._fsp--;
                        }
                        break;
                    }
                    String re_state = (st != null ? input.toString(st.getStart(), st.getStop()) : null);
                    if (re_state == null) re_state = "alive";
                    String param_number = (para != null ? para.getText() : null);
                    param_number = param_number.substring(1);
                    EVMDDSMCGenerator.addRequiresParamAPTS((ap != null ? input.toString(ap.getStart(), ap.getStop()) : null), re_state, param_number);
                }
                break;
                case 3: {
                    para = (Token) match(input, PARAM, FOLLOW_PARAM_in_re_accesspermission_typestates2011);
                    match(input, OPERATOR, FOLLOW_OPERATOR_in_re_accesspermission_typestates2013);
                    match(input, ID, FOLLOW_ID_in_re_accesspermission_typestates2015);
                }
                break;
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public final void enaccesspermissiontypestates() throws RecognitionException {
        Token para = null;
        PluralParser.AccesspermissionReturn ap = null;
        PluralParser.TypestateReturn st = null;
        try {
            int alt40 = 4;
            int LA40_0 = input.LA(1);
            if (((LA40_0 >= FULL && LA40_0 <= NONE))) {
                int LA40_1 = input.LA(2);
                if ((LA40_1 == LSBRACKET)) {
                    switch (input.LA(3)) {
                        case THIS: {
                            alt40 = 1;
                        }
                        break;
                        case RESULT: {
                            alt40 = 2;
                        }
                        break;
                        case PARAM: {
                            alt40 = 3;
                        }
                        break;
                        default:
                            NoViableAltException nvae = new NoViableAltException("", 40, 3, input);
                            throw nvae;
                    }
                } else {
                    NoViableAltException nvae = new NoViableAltException("", 40, 1, input);
                    throw nvae;
                }
            } else if ((LA40_0 == PARAM)) {
                alt40 = 4;
            } else {
                NoViableAltException nvae = new NoViableAltException("", 40, 0, input);
                throw nvae;
            }
            switch (alt40) {
                case 1: {
                    pushFollow(FOLLOW_accesspermission_in_en_accesspermission_typestates2146);
                    ap = accesspermission();
                    state._fsp--;
                    match(input, LSBRACKET, FOLLOW_LSBRACKET_in_en_accesspermission_typestates2148);
                    match(input, THIS, FOLLOW_THIS_in_en_accesspermission_typestates2150);
                    match(input, RSBRACKET, FOLLOW_RSBRACKET_in_en_accesspermission_typestates2152);
                    int alt37 = 2;
                    int LA37_0 = input.LA(1);
                    if ((LA37_0 == IN)) {
                        alt37 = 1;
                    }
                    switch (alt37) {
                        case 1: {
                            match(input, IN, FOLLOW_IN_in_en_accesspermission_typestates2155);
                            pushFollow(FOLLOW_typestate_in_en_accesspermission_typestates2159);
                            st = typestate();
                            state._fsp--;
                        }
                        break;
                    }
                    String en_state = (st != null ? input.toString(st.getStart(), st.getStop()) : null);
                    if (en_state == null) en_state = "alive";
                    EVMDDSMCGenerator.addEnsuresAPTS((ap != null ? input.toString(ap.getStart(), ap.getStop()) : null), en_state);
                }
                break;
                case 2: {
                    pushFollow(FOLLOW_accesspermission_in_en_accesspermission_typestates2199);
                    ap = accesspermission();
                    state._fsp--;
                    match(input, LSBRACKET, FOLLOW_LSBRACKET_in_en_accesspermission_typestates2201);
                    match(input, RESULT, FOLLOW_RESULT_in_en_accesspermission_typestates2203);
                    match(input, RSBRACKET, FOLLOW_RSBRACKET_in_en_accesspermission_typestates2205);
                    int alt38 = 2;
                    int LA38_0 = input.LA(1);
                    if ((LA38_0 == IN)) {
                        alt38 = 1;
                    }
                    switch (alt38) {
                        case 1: {
                            match(input, IN, FOLLOW_IN_in_en_accesspermission_typestates2208);
                            pushFollow(FOLLOW_typestate_in_en_accesspermission_typestates2212);
                            st = typestate();
                            state._fsp--;
                        }
                        break;
                    }
                    String en_state = (st != null ? input.toString(st.getStart(), st.getStop()) : null);
                    if (en_state == null) en_state = "alive";
                    EVMDDSMCGenerator.addEnsuresResultAPTS((ap != null ? input.toString(ap.getStart(), ap.getStop()) : null), en_state);
                }
                break;
                case 3: {
                    pushFollow(FOLLOW_accesspermission_in_en_accesspermission_typestates2252);
                    ap = accesspermission();
                    state._fsp--;
                    match(input, LSBRACKET, FOLLOW_LSBRACKET_in_en_accesspermission_typestates2254);
                    para = (Token) match(input, PARAM, FOLLOW_PARAM_in_en_accesspermission_typestates2258);
                    match(input, RSBRACKET, FOLLOW_RSBRACKET_in_en_accesspermission_typestates2260);
                    int alt39 = 2;
                    int LA39_0 = input.LA(1);
                    if ((LA39_0 == IN)) {
                        alt39 = 1;
                    }
                    switch (alt39) {
                        case 1: {
                            match(input, IN, FOLLOW_IN_in_en_accesspermission_typestates2263);
                            pushFollow(FOLLOW_typestate_in_en_accesspermission_typestates2267);
                            st = typestate();
                            state._fsp--;
                        }
                        break;
                    }
                    String en_state = (st != null ? input.toString(st.getStart(), st.getStop()) : null);
                    if (en_state == null) en_state = "alive";
                    String param_number = (para != null ? para.getText() : null);
                    param_number = param_number.substring(1);
                    EVMDDSMCGenerator.addEnsuresParamAPTS((ap != null ? input.toString(ap.getStart(), ap.getStop()) : null), en_state, param_number);
                }
                break;
                case 4: {
                    para = (Token) match(input, PARAM, FOLLOW_PARAM_in_en_accesspermission_typestates2309);
                    match(input, OPERATOR, FOLLOW_OPERATOR_in_en_accesspermission_typestates2311);
                    match(input, ID, FOLLOW_ID_in_en_accesspermission_typestates2313);
                }
                break;
            }
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return;
    }

    public static class TypestateReturn extends ParserRuleReturnScope {
    }

    public final PluralParser.TypestateReturn typestate() throws RecognitionException {
        PluralParser.TypestateReturn retval = new PluralParser.TypestateReturn();
        retval.getStart() = input.LT(1);
        try {
            {
                match(input, ID, FOLLOW_ID_in_typestate2373);
            }
            retval.getStop() = input.LT(-1);
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return retval;
    }

    public static class AtApPermissionReturn extends ParserRuleReturnScope {
    }

    public final PluralParser.AtApPermissionReturn atappermission() throws RecognitionException {
        PluralParser.AtApPermissionReturn retval = new PluralParser.AtApPermissionReturn();
        retval.getStart() = input.LT(1);
        try {
            {
                if ((input.LA(1) >= AT_FULL && input.LA(1) <= AT_UNIQUE)) {
                    input.consume();
                    state.errorRecovery = false;
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    throw mse;
                }
            }
            retval.getStop() = input.LT(-1);
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return retval;
    }

    public static class AccesspermissionReturn extends ParserRuleReturnScope {
    }

    public final PluralParser.AccesspermissionReturn accesspermission() throws RecognitionException {
        PluralParser.AccesspermissionReturn retval = new PluralParser.AccesspermissionReturn();
        retval.getStart() = input.LT(1);
        try {
            {
                if ((input.LA(1) >= FULL && input.LA(1) <= NONE)) {
                    input.consume();
                    state.errorRecovery = false;
                } else {
                    MismatchedSetException mse = new MismatchedSetException(null, input);
                    throw mse;
                }
            }
            retval.getStop() = input.LT(-1);
        } catch (RecognitionException re) {
            reportError(re);
            recover(input, re);
        } finally {
        }
        return retval;
    }

    public static final BitSet FOLLOW_jmlClassSpecifications_in_jmlSpecifications1071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_jmlMethodSpecification_in_jmlSpecifications1073 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_jmlGhostDeclaration_in_jmlClassSpecifications1081 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_jmlGhostInv_in_jmlClassSpecifications1083 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GHOST_in_jmlGhostDeclaration1090 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_INT_in_jmlGhostDeclaration1092 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_ID_in_jmlGhostDeclaration1096 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_jmlGhostDeclaration1098 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INVARIANT_in_jmlGhostInv1106 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_NUMBERS_in_jmlGhostInv1111 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_LESSTHANEQUAL_in_jmlGhostInv1113 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_ID_in_jmlGhostInv1115 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_ANDD_in_jmlGhostInv1117 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_ID_in_jmlGhostInv1119 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_LESSTHANEQUAL_in_jmlGhostInv1121 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_NUMBERS_in_jmlGhostInv1125 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_jmlGhostInv1127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_JMLSTART_in_jmlMethodSpecification1136 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_PUBLIC_BEHAVIOR_in_jmlMethodSpecification1138 = new BitSet(new long[]{0x0050000006000000L});
    public static final BitSet FOLLOW_REQUIRES_in_jmlMethodSpecification1142 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_jmlRequires_in_jmlMethodSpecification1144 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_jmlMethodSpecification1147 = new BitSet(new long[]{0x0050000004000000L});
    public static final BitSet FOLLOW_jmlAssign_in_jmlMethodSpecification1152 = new BitSet(new long[]{0x0010000004000000L});
    public static final BitSet FOLLOW_jmlEnsures_in_jmlMethodSpecification1157 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_JMLEND_in_jmlMethodSpecification1161 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_jmlReq_in_jmlRequires1167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_jmlOrReq_in_jmlRequires1169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_jmlLessThanEqualReq_in_jmlRequires1171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_jmlReq_in_jmlOrReq1178 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_OR_in_jmlOrReq1181 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_jmlReq_in_jmlOrReq1183 = new BitSet(new long[]{0x0004000000000002L});
    public static final BitSet FOLLOW_ID_in_jmlLessThanEqualReq1192 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_LESSTHANEQUAL_in_jmlLessThanEqualReq1194 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_NUMBERS_in_jmlLessThanEqualReq1198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_jmlReq1207 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_EQUALOPERATOR_in_jmlReq1209 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_NUMBERS_in_jmlReq1213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_jmlEns_in_jmlEnsures1221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_jmlOldEns_in_jmlEnsures1223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ENSURES_in_jmlOldEns1229 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_ID_in_jmlOldEns1231 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_EQUALOPERATOR_in_jmlOldEns1233 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_OLD_in_jmlOldEns1235 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_LSBRACKET_in_jmlOldEns1237 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_ID_in_jmlOldEns1239 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RSBRACKET_in_jmlOldEns1241 = new BitSet(new long[]{0x0020100000000000L});
    public static final BitSet FOLLOW_PLUSMINUSOPERATOR_in_jmlOldEns1246 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_NUMBERS_in_jmlOldEns1250 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_jmlOldEns1254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ENSURES_in_jmlEns1263 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_ID_in_jmlEns1265 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_EQUALOPERATOR_in_jmlEns1267 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_NUMBERS_in_jmlEns1271 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_jmlEns1273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ASSIGNABLE_in_jmlAssign1280 = new BitSet(new long[]{0x2180000000000000L});
    public static final BitSet FOLLOW_EVERYTHING_in_jmlAssign1285 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_NOTHING_in_jmlAssign1289 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_ID_in_jmlAssign1293 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_jmlAssign1296 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_perm_in_specifications1309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cases_in_specifications1311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_classstates_in_specifications1313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_refine_in_specifications1315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REFINE_in_refine1322 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_LSBRACKET_in_refine1324 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_LCBRACKET_in_refine1326 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_states_in_refine1329 = new BitSet(new long[]{0x0000000480000000L});
    public static final BitSet FOLLOW_PUNCTUATION_in_refine1333 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_states_in_refine1335 = new BitSet(new long[]{0x0000000480000000L});
    public static final BitSet FOLLOW_RCBRACKET_in_refine1340 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RSBRACKET_in_refine1342 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STATES_in_states1351 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_LSBRACKET_in_states1353 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_dimension_in_states1355 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_PUNCTUATION_in_states1357 = new BitSet(new long[]{0x0000002000020000L});
    public static final BitSet FOLLOW_value_in_states1360 = new BitSet(new long[]{0x0000002000020000L});
    public static final BitSet FOLLOW_RSBRACKET_in_states1364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DIM_in_dimension1373 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_EQUAL_in_dimension1375 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_QUOTE_in_dimension1377 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_ID_in_dimension1381 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_QUOTE_in_dimension1383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VALUE_in_value1393 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_EQUAL_in_value1395 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_LCBRACKET_in_value1397 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_item_in_value1399 = new BitSet(new long[]{0x0000000480000000L});
    public static final BitSet FOLLOW_PUNCTUATION_in_value1402 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_item_in_value1404 = new BitSet(new long[]{0x0000000480000000L});
    public static final BitSet FOLLOW_RCBRACKET_in_value1408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QUOTE_in_item1415 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_ID_in_item1419 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_QUOTE_in_item1421 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_start_classstates_in_classstates1432 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_state_in_classstates1434 = new BitSet(new long[]{0x0000000480000000L});
    public static final BitSet FOLLOW_PUNCTUATION_in_classstates1437 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_state_in_classstates1439 = new BitSet(new long[]{0x0000000480000000L});
    public static final BitSet FOLLOW_end_classstates_in_classstates1443 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_STATES_in_start_classstates1450 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_LSBRACKET_in_start_classstates1452 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_LCBRACKET_in_start_classstates1454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RCBRACKET_in_end_classstates1461 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RSBRACKET_in_end_classstates1463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STATE_in_state1471 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_LSBRACKET_in_state1473 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_NAME_in_state1475 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_EQUAL_in_state1477 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_QUOTE_in_state1479 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_ID_in_state1483 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_QUOTE_in_state1485 = new BitSet(new long[]{0x0000000080020000L});
    public static final BitSet FOLLOW_PUNCTUATION_in_state1490 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_invariant_in_state1492 = new BitSet(new long[]{0x0000000080020000L});
    public static final BitSet FOLLOW_RSBRACKET_in_state1496 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INV_in_invariant1591 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_EQUAL_in_invariant1593 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_QUOTE_in_invariant1595 = new BitSet(new long[]{0x200000000800FC00L});
    public static final BitSet FOLLOW_condition_in_invariant1598 = new BitSet(new long[]{0x0000000018000000L});
    public static final BitSet FOLLOW_AND_in_invariant1601 = new BitSet(new long[]{0x200000000000FC00L});
    public static final BitSet FOLLOW_condition_in_invariant1603 = new BitSet(new long[]{0x0000000018000000L});
    public static final BitSet FOLLOW_QUOTE_in_invariant1609 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_condition1620 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_OPERATOR_in_condition1624 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_ID_in_condition1628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_accesspermission_in_condition1646 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_LSBRACKET_in_condition1648 = new BitSet(new long[]{0x2000000000400000L});
    public static final BitSet FOLLOW_set_in_condition1652 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RSBRACKET_in_condition1658 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_IN_in_condition1661 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_ID_in_condition1665 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CASES_in_cases1676 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_LSBRACKET_in_cases1678 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_LCBRACKET_in_cases1680 = new BitSet(new long[]{0x00000000000401F0L});
    public static final BitSet FOLLOW_perm_in_cases1682 = new BitSet(new long[]{0x0000000480000000L});
    public static final BitSet FOLLOW_other_in_cases1685 = new BitSet(new long[]{0x00000000000401F0L});
    public static final BitSet FOLLOW_perm_in_cases1687 = new BitSet(new long[]{0x0000000480000000L});
    public static final BitSet FOLLOW_RCBRACKET_in_cases1691 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RSBRACKET_in_cases1693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PUNCTUATION_in_other1701 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PERM_in_perm1712 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_LSBRACKET_in_perm1714 = new BitSet(new long[]{0x0000000086020000L});
    public static final BitSet FOLLOW_requires_ensures_clause_in_perm1716 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RSBRACKET_in_perm1718 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attype_in_perm1727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_at_ap_permission_in_attype1735 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_LSBRACKET_in_attype1738 = new BitSet(new long[]{0x0000000086000002L});
    public static final BitSet FOLLOW_REQUIRES_in_attype1741 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_EQUAL_in_attype1743 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_QUOTE_in_attype1745 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_typestate_in_attype1749 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_QUOTE_in_attype1751 = new BitSet(new long[]{0x0000000084000002L});
    public static final BitSet FOLLOW_PUNCTUATION_in_attype1756 = new BitSet(new long[]{0x0000000084000002L});
    public static final BitSet FOLLOW_ENSURES_in_attype1761 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_EQUAL_in_attype1763 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_QUOTE_in_attype1765 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_typestate_in_attype1769 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_QUOTE_in_attype1771 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_PUNCTUATION_in_attype1776 = new BitSet(new long[]{0x0000002020000000L});
    public static final BitSet FOLLOW_usevalue_in_attype1778 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_USE_in_usevalue1792 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_EQUAL_in_usevalue1794 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_USEFIELDS_in_usevalue1796 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VALUE_in_usevalue1809 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_EQUAL_in_usevalue1811 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_QUOTE_in_usevalue1813 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_ID_in_usevalue1815 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_QUOTE_in_usevalue1817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_requires_clause_in_requires_ensures_clause1831 = new BitSet(new long[]{0x0000000084000002L});
    public static final BitSet FOLLOW_PUNCTUATION_in_requires_ensures_clause1836 = new BitSet(new long[]{0x0000000004000002L});
    public static final BitSet FOLLOW_ensures_clause_in_requires_ensures_clause1841 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REQUIRES_in_requires_clause1852 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_EQUAL_in_requires_clause1854 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_QUOTE_in_requires_clause1856 = new BitSet(new long[]{0x200000000100FC00L});
    public static final BitSet FOLLOW_re_accesspermission_typestates_in_requires_clause1858 = new BitSet(new long[]{0x0000000018000000L});
    public static final BitSet FOLLOW_AND_in_requires_clause1861 = new BitSet(new long[]{0x200000000100FC00L});
    public static final BitSet FOLLOW_re_accesspermission_typestates_in_requires_clause1863 = new BitSet(new long[]{0x0000000018000000L});
    public static final BitSet FOLLOW_QUOTE_in_requires_clause1867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ENSURES_in_ensures_clause1875 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_EQUAL_in_ensures_clause1877 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_QUOTE_in_ensures_clause1879 = new BitSet(new long[]{0x200000000100FC00L});
    public static final BitSet FOLLOW_en_accesspermission_typestates_in_ensures_clause1881 = new BitSet(new long[]{0x0000000018000000L});
    public static final BitSet FOLLOW_AND_in_ensures_clause1884 = new BitSet(new long[]{0x200000000100FC00L});
    public static final BitSet FOLLOW_en_accesspermission_typestates_in_ensures_clause1886 = new BitSet(new long[]{0x0000000018000000L});
    public static final BitSet FOLLOW_QUOTE_in_ensures_clause1890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_accesspermission_in_re_accesspermission_typestates1901 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_LSBRACKET_in_re_accesspermission_typestates1903 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_THIS_in_re_accesspermission_typestates1905 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RSBRACKET_in_re_accesspermission_typestates1907 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_IN_in_re_accesspermission_typestates1910 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_typestate_in_re_accesspermission_typestates1914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_accesspermission_in_re_accesspermission_typestates1954 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_LSBRACKET_in_re_accesspermission_typestates1956 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_PARAM_in_re_accesspermission_typestates1960 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RSBRACKET_in_re_accesspermission_typestates1962 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_IN_in_re_accesspermission_typestates1965 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_typestate_in_re_accesspermission_typestates1969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PARAM_in_re_accesspermission_typestates2011 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_OPERATOR_in_re_accesspermission_typestates2013 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_ID_in_re_accesspermission_typestates2015 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_accesspermission_in_en_accesspermission_typestates2146 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_LSBRACKET_in_en_accesspermission_typestates2148 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_THIS_in_en_accesspermission_typestates2150 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RSBRACKET_in_en_accesspermission_typestates2152 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_IN_in_en_accesspermission_typestates2155 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_typestate_in_en_accesspermission_typestates2159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_accesspermission_in_en_accesspermission_typestates2199 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_LSBRACKET_in_en_accesspermission_typestates2201 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_RESULT_in_en_accesspermission_typestates2203 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RSBRACKET_in_en_accesspermission_typestates2205 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_IN_in_en_accesspermission_typestates2208 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_typestate_in_en_accesspermission_typestates2212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_accesspermission_in_en_accesspermission_typestates2252 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_LSBRACKET_in_en_accesspermission_typestates2254 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_PARAM_in_en_accesspermission_typestates2258 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RSBRACKET_in_en_accesspermission_typestates2260 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_IN_in_en_accesspermission_typestates2263 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_typestate_in_en_accesspermission_typestates2267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PARAM_in_en_accesspermission_typestates2309 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_OPERATOR_in_en_accesspermission_typestates2311 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_ID_in_en_accesspermission_typestates2313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_typestate2373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_at_ap_permission0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_accesspermission0 = new BitSet(new long[]{0x0000000000000002L});

    public static BitSet getFOLLOW_ANDD_in_jmlGhostInv1117() {
        Cloner cloner = new Cloner();
        FOLLOW_ANDD_in_jmlGhostInv1117 = cloner.deepClone(FOLLOW_ANDD_in_jmlGhostInv1117);
        return FOLLOW_ANDD_in_jmlGhostInv1117;
    }

    public static BitSet getFOLLOW_STATE_in_state1471() {
        Cloner cloner = new Cloner();
        FOLLOW_STATE_in_state1471 = cloner.deepClone(FOLLOW_STATE_in_state1471);
        return FOLLOW_STATE_in_state1471;
    }

    public static BitSet getFOLLOW_INVARIANT_in_jmlGhostInv1106() {
        Cloner cloner = new Cloner();
        FOLLOW_INVARIANT_in_jmlGhostInv1106 = cloner.deepClone(FOLLOW_INVARIANT_in_jmlGhostInv1106);
        return FOLLOW_INVARIANT_in_jmlGhostInv1106;
    }

    public static BitSet getFOLLOW_RSBRACKET_in_en_accesspermission_typestates2205() {
        Cloner cloner = new Cloner();
        FOLLOW_RSBRACKET_in_en_accesspermission_typestates2205 = cloner.deepClone(FOLLOW_RSBRACKET_in_en_accesspermission_typestates2205);
        return FOLLOW_RSBRACKET_in_en_accesspermission_typestates2205;
    }

    public static BitSet getFOLLOW_JMLSTART_in_jmlMethodSpecification1136() {
        Cloner cloner = new Cloner();
        FOLLOW_JMLSTART_in_jmlMethodSpecification1136 = cloner.deepClone(FOLLOW_JMLSTART_in_jmlMethodSpecification1136);
        return FOLLOW_JMLSTART_in_jmlMethodSpecification1136;
    }

    public static BitSet getFOLLOW_SEMICOLON_in_jmlMethodSpecification1147() {
        Cloner cloner = new Cloner();
        FOLLOW_SEMICOLON_in_jmlMethodSpecification1147 = cloner.deepClone(FOLLOW_SEMICOLON_in_jmlMethodSpecification1147);
        return FOLLOW_SEMICOLON_in_jmlMethodSpecification1147;
    }

    public static BitSet getFOLLOW_LCBRACKET_in_refine1326() {
        Cloner cloner = new Cloner();
        FOLLOW_LCBRACKET_in_refine1326 = cloner.deepClone(FOLLOW_LCBRACKET_in_refine1326);
        return FOLLOW_LCBRACKET_in_refine1326;
    }

    public static BitSet getFOLLOW_EQUAL_in_invariant1593() {
        Cloner cloner = new Cloner();
        FOLLOW_EQUAL_in_invariant1593 = cloner.deepClone(FOLLOW_EQUAL_in_invariant1593);
        return FOLLOW_EQUAL_in_invariant1593;
    }

    public static BitSet getFOLLOW_accesspermission_in_en_accesspermission_typestates2199() {
        Cloner cloner = new Cloner();
        FOLLOW_accesspermission_in_en_accesspermission_typestates2199 = cloner.deepClone(FOLLOW_accesspermission_in_en_accesspermission_typestates2199);
        return FOLLOW_accesspermission_in_en_accesspermission_typestates2199;
    }

    public static BitSet getFOLLOW_jmlOrReq_in_jmlRequires1169() {
        Cloner cloner = new Cloner();
        FOLLOW_jmlOrReq_in_jmlRequires1169 = cloner.deepClone(FOLLOW_jmlOrReq_in_jmlRequires1169);
        return FOLLOW_jmlOrReq_in_jmlRequires1169;
    }

    public static BitSet getFOLLOW_ID_in_dimension1381() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_dimension1381 = cloner.deepClone(FOLLOW_ID_in_dimension1381);
        return FOLLOW_ID_in_dimension1381;
    }

    public static BitSet getFOLLOW_QUOTE_in_attype1745() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_attype1745 = cloner.deepClone(FOLLOW_QUOTE_in_attype1745);
        return FOLLOW_QUOTE_in_attype1745;
    }

    public static BitSet getFOLLOW_jmlAssign_in_jmlMethodSpecification1152() {
        Cloner cloner = new Cloner();
        FOLLOW_jmlAssign_in_jmlMethodSpecification1152 = cloner.deepClone(FOLLOW_jmlAssign_in_jmlMethodSpecification1152);
        return FOLLOW_jmlAssign_in_jmlMethodSpecification1152;
    }

    public static BitSet getFOLLOW_LSBRACKET_in_en_accesspermission_typestates2254() {
        Cloner cloner = new Cloner();
        FOLLOW_LSBRACKET_in_en_accesspermission_typestates2254 = cloner.deepClone(FOLLOW_LSBRACKET_in_en_accesspermission_typestates2254);
        return FOLLOW_LSBRACKET_in_en_accesspermission_typestates2254;
    }

    public static BitSet getFOLLOW_jmlReq_in_jmlOrReq1183() {
        Cloner cloner = new Cloner();
        FOLLOW_jmlReq_in_jmlOrReq1183 = cloner.deepClone(FOLLOW_jmlReq_in_jmlOrReq1183);
        return FOLLOW_jmlReq_in_jmlOrReq1183;
    }

    public static BitSet getFOLLOW_perm_in_cases1687() {
        Cloner cloner = new Cloner();
        FOLLOW_perm_in_cases1687 = cloner.deepClone(FOLLOW_perm_in_cases1687);
        return FOLLOW_perm_in_cases1687;
    }

    public static BitSet getFOLLOW_QUOTE_in_item1421() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_item1421 = cloner.deepClone(FOLLOW_QUOTE_in_item1421);
        return FOLLOW_QUOTE_in_item1421;
    }

    public static BitSet getFOLLOW_QUOTE_in_invariant1595() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_invariant1595 = cloner.deepClone(FOLLOW_QUOTE_in_invariant1595);
        return FOLLOW_QUOTE_in_invariant1595;
    }

    public static BitSet getFOLLOW_RESULT_in_en_accesspermission_typestates2203() {
        Cloner cloner = new Cloner();
        FOLLOW_RESULT_in_en_accesspermission_typestates2203 = cloner.deepClone(FOLLOW_RESULT_in_en_accesspermission_typestates2203);
        return FOLLOW_RESULT_in_en_accesspermission_typestates2203;
    }

    public static BitSet getFOLLOW_en_accesspermission_typestates_in_ensures_clause1886() {
        Cloner cloner = new Cloner();
        FOLLOW_en_accesspermission_typestates_in_ensures_clause1886 = cloner.deepClone(FOLLOW_en_accesspermission_typestates_in_ensures_clause1886);
        return FOLLOW_en_accesspermission_typestates_in_ensures_clause1886;
    }

    public static BitSet getFOLLOW_RSBRACKET_in_en_accesspermission_typestates2152() {
        Cloner cloner = new Cloner();
        FOLLOW_RSBRACKET_in_en_accesspermission_typestates2152 = cloner.deepClone(FOLLOW_RSBRACKET_in_en_accesspermission_typestates2152);
        return FOLLOW_RSBRACKET_in_en_accesspermission_typestates2152;
    }

    public static BitSet getFOLLOW_ENSURES_in_ensures_clause1875() {
        Cloner cloner = new Cloner();
        FOLLOW_ENSURES_in_ensures_clause1875 = cloner.deepClone(FOLLOW_ENSURES_in_ensures_clause1875);
        return FOLLOW_ENSURES_in_ensures_clause1875;
    }

    public static BitSet getFOLLOW_IN_in_en_accesspermission_typestates2263() {
        Cloner cloner = new Cloner();
        FOLLOW_IN_in_en_accesspermission_typestates2263 = cloner.deepClone(FOLLOW_IN_in_en_accesspermission_typestates2263);
        return FOLLOW_IN_in_en_accesspermission_typestates2263;
    }

    public static BitSet getFOLLOW_QUOTE_in_dimension1383() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_dimension1383 = cloner.deepClone(FOLLOW_QUOTE_in_dimension1383);
        return FOLLOW_QUOTE_in_dimension1383;
    }

    public static BitSet getFOLLOW_CASES_in_cases1676() {
        Cloner cloner = new Cloner();
        FOLLOW_CASES_in_cases1676 = cloner.deepClone(FOLLOW_CASES_in_cases1676);
        return FOLLOW_CASES_in_cases1676;
    }

    public static BitSet getFOLLOW_ASSIGNABLE_in_jmlAssign1280() {
        Cloner cloner = new Cloner();
        FOLLOW_ASSIGNABLE_in_jmlAssign1280 = cloner.deepClone(FOLLOW_ASSIGNABLE_in_jmlAssign1280);
        return FOLLOW_ASSIGNABLE_in_jmlAssign1280;
    }

    public static BitSet getFOLLOW_states_in_refine1329() {
        Cloner cloner = new Cloner();
        FOLLOW_states_in_refine1329 = cloner.deepClone(FOLLOW_states_in_refine1329);
        return FOLLOW_states_in_refine1329;
    }

    public static BitSet getFOLLOW_PUNCTUATION_in_refine1333() {
        Cloner cloner = new Cloner();
        FOLLOW_PUNCTUATION_in_refine1333 = cloner.deepClone(FOLLOW_PUNCTUATION_in_refine1333);
        return FOLLOW_PUNCTUATION_in_refine1333;
    }

    public static BitSet getFOLLOW_jmlMethodSpecification_in_jmlSpecifications1073() {
        Cloner cloner = new Cloner();
        FOLLOW_jmlMethodSpecification_in_jmlSpecifications1073 = cloner.deepClone(FOLLOW_jmlMethodSpecification_in_jmlSpecifications1073);
        return FOLLOW_jmlMethodSpecification_in_jmlSpecifications1073;
    }

    public static BitSet getFOLLOW_accesspermission_in_condition1646() {
        Cloner cloner = new Cloner();
        FOLLOW_accesspermission_in_condition1646 = cloner.deepClone(FOLLOW_accesspermission_in_condition1646);
        return FOLLOW_accesspermission_in_condition1646;
    }

    public static BitSet getFOLLOW_RSBRACKET_in_states1364() {
        Cloner cloner = new Cloner();
        FOLLOW_RSBRACKET_in_states1364 = cloner.deepClone(FOLLOW_RSBRACKET_in_states1364);
        return FOLLOW_RSBRACKET_in_states1364;
    }

    public static BitSet getFOLLOW_ID_in_jmlGhostInv1119() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_jmlGhostInv1119 = cloner.deepClone(FOLLOW_ID_in_jmlGhostInv1119);
        return FOLLOW_ID_in_jmlGhostInv1119;
    }

    public static BitSet getFOLLOW_PERM_in_perm1712() {
        Cloner cloner = new Cloner();
        FOLLOW_PERM_in_perm1712 = cloner.deepClone(FOLLOW_PERM_in_perm1712);
        return FOLLOW_PERM_in_perm1712;
    }

    public static BitSet getFOLLOW_perm_in_specifications1309() {
        Cloner cloner = new Cloner();
        FOLLOW_perm_in_specifications1309 = cloner.deepClone(FOLLOW_perm_in_specifications1309);
        return FOLLOW_perm_in_specifications1309;
    }

    public static BitSet getFOLLOW_ensures_clause_in_requires_ensures_clause1841() {
        Cloner cloner = new Cloner();
        FOLLOW_ensures_clause_in_requires_ensures_clause1841 = cloner.deepClone(FOLLOW_ensures_clause_in_requires_ensures_clause1841);
        return FOLLOW_ensures_clause_in_requires_ensures_clause1841;
    }

    public static BitSet getFOLLOW_QUOTE_in_ensures_clause1890() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_ensures_clause1890 = cloner.deepClone(FOLLOW_QUOTE_in_ensures_clause1890);
        return FOLLOW_QUOTE_in_ensures_clause1890;
    }

    public static BitSet getFOLLOW_RCBRACKET_in_end_classstates1461() {
        Cloner cloner = new Cloner();
        FOLLOW_RCBRACKET_in_end_classstates1461 = cloner.deepClone(FOLLOW_RCBRACKET_in_end_classstates1461);
        return FOLLOW_RCBRACKET_in_end_classstates1461;
    }

    public static BitSet getFOLLOW_PUNCTUATION_in_other1701() {
        Cloner cloner = new Cloner();
        FOLLOW_PUNCTUATION_in_other1701 = cloner.deepClone(FOLLOW_PUNCTUATION_in_other1701);
        return FOLLOW_PUNCTUATION_in_other1701;
    }

    public static BitSet getFOLLOW_LCBRACKET_in_cases1680() {
        Cloner cloner = new Cloner();
        FOLLOW_LCBRACKET_in_cases1680 = cloner.deepClone(FOLLOW_LCBRACKET_in_cases1680);
        return FOLLOW_LCBRACKET_in_cases1680;
    }

    public static BitSet getFOLLOW_at_ap_permission_in_attype1735() {
        Cloner cloner = new Cloner();
        FOLLOW_at_ap_permission_in_attype1735 = cloner.deepClone(FOLLOW_at_ap_permission_in_attype1735);
        return FOLLOW_at_ap_permission_in_attype1735;
    }

    public static BitSet getFOLLOW_NUMBERS_in_jmlLessThanEqualReq1198() {
        Cloner cloner = new Cloner();
        FOLLOW_NUMBERS_in_jmlLessThanEqualReq1198 = cloner.deepClone(FOLLOW_NUMBERS_in_jmlLessThanEqualReq1198);
        return FOLLOW_NUMBERS_in_jmlLessThanEqualReq1198;
    }

    public static BitSet getFOLLOW_SEMICOLON_in_jmlEns1273() {
        Cloner cloner = new Cloner();
        FOLLOW_SEMICOLON_in_jmlEns1273 = cloner.deepClone(FOLLOW_SEMICOLON_in_jmlEns1273);
        return FOLLOW_SEMICOLON_in_jmlEns1273;
    }

    public static BitSet getFOLLOW_states_in_refine1335() {
        Cloner cloner = new Cloner();
        FOLLOW_states_in_refine1335 = cloner.deepClone(FOLLOW_states_in_refine1335);
        return FOLLOW_states_in_refine1335;
    }

    public static BitSet getFOLLOW_RSBRACKET_in_cases1693() {
        Cloner cloner = new Cloner();
        FOLLOW_RSBRACKET_in_cases1693 = cloner.deepClone(FOLLOW_RSBRACKET_in_cases1693);
        return FOLLOW_RSBRACKET_in_cases1693;
    }

    public static BitSet getFOLLOW_PARAM_in_en_accesspermission_typestates2258() {
        Cloner cloner = new Cloner();
        FOLLOW_PARAM_in_en_accesspermission_typestates2258 = cloner.deepClone(FOLLOW_PARAM_in_en_accesspermission_typestates2258);
        return FOLLOW_PARAM_in_en_accesspermission_typestates2258;
    }

    public static BitSet getFOLLOW_typestate_in_re_accesspermission_typestates1969() {
        Cloner cloner = new Cloner();
        FOLLOW_typestate_in_re_accesspermission_typestates1969 = cloner.deepClone(FOLLOW_typestate_in_re_accesspermission_typestates1969);
        return FOLLOW_typestate_in_re_accesspermission_typestates1969;
    }

    public static BitSet getFOLLOW_typestate_in_en_accesspermission_typestates2159() {
        Cloner cloner = new Cloner();
        FOLLOW_typestate_in_en_accesspermission_typestates2159 = cloner.deepClone(FOLLOW_typestate_in_en_accesspermission_typestates2159);
        return FOLLOW_typestate_in_en_accesspermission_typestates2159;
    }

    public static BitSet getFOLLOW_SEMICOLON_in_jmlGhostInv1127() {
        Cloner cloner = new Cloner();
        FOLLOW_SEMICOLON_in_jmlGhostInv1127 = cloner.deepClone(FOLLOW_SEMICOLON_in_jmlGhostInv1127);
        return FOLLOW_SEMICOLON_in_jmlGhostInv1127;
    }

    public static BitSet getFOLLOW_LSBRACKET_in_re_accesspermission_typestates1903() {
        Cloner cloner = new Cloner();
        FOLLOW_LSBRACKET_in_re_accesspermission_typestates1903 = cloner.deepClone(FOLLOW_LSBRACKET_in_re_accesspermission_typestates1903);
        return FOLLOW_LSBRACKET_in_re_accesspermission_typestates1903;
    }

    public static BitSet getFOLLOW_ID_in_jmlGhostDeclaration1096() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_jmlGhostDeclaration1096 = cloner.deepClone(FOLLOW_ID_in_jmlGhostDeclaration1096);
        return FOLLOW_ID_in_jmlGhostDeclaration1096;
    }

    public static BitSet getFOLLOW_IN_in_en_accesspermission_typestates2155() {
        Cloner cloner = new Cloner();
        FOLLOW_IN_in_en_accesspermission_typestates2155 = cloner.deepClone(FOLLOW_IN_in_en_accesspermission_typestates2155);
        return FOLLOW_IN_in_en_accesspermission_typestates2155;
    }

    public static BitSet getFOLLOW_attype_in_perm1727() {
        Cloner cloner = new Cloner();
        FOLLOW_attype_in_perm1727 = cloner.deepClone(FOLLOW_attype_in_perm1727);
        return FOLLOW_attype_in_perm1727;
    }

    public static BitSet getFOLLOW_jmlGhostInv_in_jmlClassSpecifications1083() {
        Cloner cloner = new Cloner();
        FOLLOW_jmlGhostInv_in_jmlClassSpecifications1083 = cloner.deepClone(FOLLOW_jmlGhostInv_in_jmlClassSpecifications1083);
        return FOLLOW_jmlGhostInv_in_jmlClassSpecifications1083;
    }

    public static BitSet getFOLLOW_EVERYTHING_in_jmlAssign1285() {
        Cloner cloner = new Cloner();
        FOLLOW_EVERYTHING_in_jmlAssign1285 = cloner.deepClone(FOLLOW_EVERYTHING_in_jmlAssign1285);
        return FOLLOW_EVERYTHING_in_jmlAssign1285;
    }

    public static BitSet getFOLLOW_OPERATOR_in_en_accesspermission_typestates2311() {
        Cloner cloner = new Cloner();
        FOLLOW_OPERATOR_in_en_accesspermission_typestates2311 = cloner.deepClone(FOLLOW_OPERATOR_in_en_accesspermission_typestates2311);
        return FOLLOW_OPERATOR_in_en_accesspermission_typestates2311;
    }

    public static BitSet getFOLLOW_accesspermission_in_en_accesspermission_typestates2146() {
        Cloner cloner = new Cloner();
        FOLLOW_accesspermission_in_en_accesspermission_typestates2146 = cloner.deepClone(FOLLOW_accesspermission_in_en_accesspermission_typestates2146);
        return FOLLOW_accesspermission_in_en_accesspermission_typestates2146;
    }

    public static BitSet getFOLLOW_EQUAL_in_requires_clause1854() {
        Cloner cloner = new Cloner();
        FOLLOW_EQUAL_in_requires_clause1854 = cloner.deepClone(FOLLOW_EQUAL_in_requires_clause1854);
        return FOLLOW_EQUAL_in_requires_clause1854;
    }

    public static BitSet getFOLLOW_jmlOldEns_in_jmlEnsures1223() {
        Cloner cloner = new Cloner();
        FOLLOW_jmlOldEns_in_jmlEnsures1223 = cloner.deepClone(FOLLOW_jmlOldEns_in_jmlEnsures1223);
        return FOLLOW_jmlOldEns_in_jmlEnsures1223;
    }

    public static BitSet getFOLLOW_QUOTE_in_requires_clause1856() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_requires_clause1856 = cloner.deepClone(FOLLOW_QUOTE_in_requires_clause1856);
        return FOLLOW_QUOTE_in_requires_clause1856;
    }

    public static BitSet getFOLLOW_EQUAL_in_dimension1375() {
        Cloner cloner = new Cloner();
        FOLLOW_EQUAL_in_dimension1375 = cloner.deepClone(FOLLOW_EQUAL_in_dimension1375);
        return FOLLOW_EQUAL_in_dimension1375;
    }

    public static BitSet getFOLLOW_IN_in_condition1661() {
        Cloner cloner = new Cloner();
        FOLLOW_IN_in_condition1661 = cloner.deepClone(FOLLOW_IN_in_condition1661);
        return FOLLOW_IN_in_condition1661;
    }

    public static BitSet getFOLLOW_NUMBERS_in_jmlEns1271() {
        Cloner cloner = new Cloner();
        FOLLOW_NUMBERS_in_jmlEns1271 = cloner.deepClone(FOLLOW_NUMBERS_in_jmlEns1271);
        return FOLLOW_NUMBERS_in_jmlEns1271;
    }

    public static BitSet getFOLLOW_RSBRACKET_in_en_accesspermission_typestates2260() {
        Cloner cloner = new Cloner();
        FOLLOW_RSBRACKET_in_en_accesspermission_typestates2260 = cloner.deepClone(FOLLOW_RSBRACKET_in_en_accesspermission_typestates2260);
        return FOLLOW_RSBRACKET_in_en_accesspermission_typestates2260;
    }

    public static BitSet getFOLLOW_INT_in_jmlGhostDeclaration1092() {
        Cloner cloner = new Cloner();
        FOLLOW_INT_in_jmlGhostDeclaration1092 = cloner.deepClone(FOLLOW_INT_in_jmlGhostDeclaration1092);
        return FOLLOW_INT_in_jmlGhostDeclaration1092;
    }

    public static BitSet getFOLLOW_ID_in_item1419() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_item1419 = cloner.deepClone(FOLLOW_ID_in_item1419);
        return FOLLOW_ID_in_item1419;
    }

    public static BitSet getFOLLOW_LSBRACKET_in_state1473() {
        Cloner cloner = new Cloner();
        FOLLOW_LSBRACKET_in_state1473 = cloner.deepClone(FOLLOW_LSBRACKET_in_state1473);
        return FOLLOW_LSBRACKET_in_state1473;
    }

    public static BitSet getFOLLOW_ID_in_typestate2373() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_typestate2373 = cloner.deepClone(FOLLOW_ID_in_typestate2373);
        return FOLLOW_ID_in_typestate2373;
    }

    public static BitSet getFOLLOW_ID_in_jmlOldEns1239() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_jmlOldEns1239 = cloner.deepClone(FOLLOW_ID_in_jmlOldEns1239);
        return FOLLOW_ID_in_jmlOldEns1239;
    }

    public static BitSet getFOLLOW_RSBRACKET_in_condition1658() {
        Cloner cloner = new Cloner();
        FOLLOW_RSBRACKET_in_condition1658 = cloner.deepClone(FOLLOW_RSBRACKET_in_condition1658);
        return FOLLOW_RSBRACKET_in_condition1658;
    }

    public static BitSet getFOLLOW_LCBRACKET_in_value1397() {
        Cloner cloner = new Cloner();
        FOLLOW_LCBRACKET_in_value1397 = cloner.deepClone(FOLLOW_LCBRACKET_in_value1397);
        return FOLLOW_LCBRACKET_in_value1397;
    }

    public static BitSet getFOLLOW_refine_in_specifications1315() {
        Cloner cloner = new Cloner();
        FOLLOW_refine_in_specifications1315 = cloner.deepClone(FOLLOW_refine_in_specifications1315);
        return FOLLOW_refine_in_specifications1315;
    }

    public static BitSet getFOLLOW_AND_in_requires_clause1861() {
        Cloner cloner = new Cloner();
        FOLLOW_AND_in_requires_clause1861 = cloner.deepClone(FOLLOW_AND_in_requires_clause1861);
        return FOLLOW_AND_in_requires_clause1861;
    }

    public static BitSet getFOLLOW_NAME_in_state1475() {
        Cloner cloner = new Cloner();
        FOLLOW_NAME_in_state1475 = cloner.deepClone(FOLLOW_NAME_in_state1475);
        return FOLLOW_NAME_in_state1475;
    }

    public static BitSet getFOLLOW_QUOTE_in_state1479() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_state1479 = cloner.deepClone(FOLLOW_QUOTE_in_state1479);
        return FOLLOW_QUOTE_in_state1479;
    }

    public static BitSet getFOLLOW_NOTHING_in_jmlAssign1289() {
        Cloner cloner = new Cloner();
        FOLLOW_NOTHING_in_jmlAssign1289 = cloner.deepClone(FOLLOW_NOTHING_in_jmlAssign1289);
        return FOLLOW_NOTHING_in_jmlAssign1289;
    }

    public static BitSet getFOLLOW_IN_in_re_accesspermission_typestates1910() {
        Cloner cloner = new Cloner();
        FOLLOW_IN_in_re_accesspermission_typestates1910 = cloner.deepClone(FOLLOW_IN_in_re_accesspermission_typestates1910);
        return FOLLOW_IN_in_re_accesspermission_typestates1910;
    }

    public static BitSet getFOLLOW_PARAM_in_en_accesspermission_typestates2309() {
        Cloner cloner = new Cloner();
        FOLLOW_PARAM_in_en_accesspermission_typestates2309 = cloner.deepClone(FOLLOW_PARAM_in_en_accesspermission_typestates2309);
        return FOLLOW_PARAM_in_en_accesspermission_typestates2309;
    }

    public static BitSet getFOLLOW_jmlLessThanEqualReq_in_jmlRequires1171() {
        Cloner cloner = new Cloner();
        FOLLOW_jmlLessThanEqualReq_in_jmlRequires1171 = cloner.deepClone(FOLLOW_jmlLessThanEqualReq_in_jmlRequires1171);
        return FOLLOW_jmlLessThanEqualReq_in_jmlRequires1171;
    }

    public static BitSet getFOLLOW_LSBRACKET_in_cases1678() {
        Cloner cloner = new Cloner();
        FOLLOW_LSBRACKET_in_cases1678 = cloner.deepClone(FOLLOW_LSBRACKET_in_cases1678);
        return FOLLOW_LSBRACKET_in_cases1678;
    }

    public static BitSet getFOLLOW_QUOTE_in_attype1771() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_attype1771 = cloner.deepClone(FOLLOW_QUOTE_in_attype1771);
        return FOLLOW_QUOTE_in_attype1771;
    }

    public static BitSet getFOLLOW_condition_in_invariant1598() {
        Cloner cloner = new Cloner();
        FOLLOW_condition_in_invariant1598 = cloner.deepClone(FOLLOW_condition_in_invariant1598);
        return FOLLOW_condition_in_invariant1598;
    }

    public static BitSet getFOLLOW_RSBRACKET_in_refine1342() {
        Cloner cloner = new Cloner();
        FOLLOW_RSBRACKET_in_refine1342 = cloner.deepClone(FOLLOW_RSBRACKET_in_refine1342);
        return FOLLOW_RSBRACKET_in_refine1342;
    }

    public static BitSet getFOLLOW_REQUIRES_in_jmlMethodSpecification1142() {
        Cloner cloner = new Cloner();
        FOLLOW_REQUIRES_in_jmlMethodSpecification1142 = cloner.deepClone(FOLLOW_REQUIRES_in_jmlMethodSpecification1142);
        return FOLLOW_REQUIRES_in_jmlMethodSpecification1142;
    }

    public static BitSet getFOLLOW_STATES_in_states1351() {
        Cloner cloner = new Cloner();
        FOLLOW_STATES_in_states1351 = cloner.deepClone(FOLLOW_STATES_in_states1351);
        return FOLLOW_STATES_in_states1351;
    }

    public static BitSet getFOLLOW_LSBRACKET_in_perm1714() {
        Cloner cloner = new Cloner();
        FOLLOW_LSBRACKET_in_perm1714 = cloner.deepClone(FOLLOW_LSBRACKET_in_perm1714);
        return FOLLOW_LSBRACKET_in_perm1714;
    }

    public static BitSet getFOLLOW_RSBRACKET_in_perm1718() {
        Cloner cloner = new Cloner();
        FOLLOW_RSBRACKET_in_perm1718 = cloner.deepClone(FOLLOW_RSBRACKET_in_perm1718);
        return FOLLOW_RSBRACKET_in_perm1718;
    }

    public static BitSet getFOLLOW_QUOTE_in_requires_clause1867() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_requires_clause1867 = cloner.deepClone(FOLLOW_QUOTE_in_requires_clause1867);
        return FOLLOW_QUOTE_in_requires_clause1867;
    }

    public static BitSet getFOLLOW_PARAM_in_re_accesspermission_typestates1960() {
        Cloner cloner = new Cloner();
        FOLLOW_PARAM_in_re_accesspermission_typestates1960 = cloner.deepClone(FOLLOW_PARAM_in_re_accesspermission_typestates1960);
        return FOLLOW_PARAM_in_re_accesspermission_typestates1960;
    }

    public static BitSet getFOLLOW_LSBRACKET_in_states1353() {
        Cloner cloner = new Cloner();
        FOLLOW_LSBRACKET_in_states1353 = cloner.deepClone(FOLLOW_LSBRACKET_in_states1353);
        return FOLLOW_LSBRACKET_in_states1353;
    }

    public static BitSet getFOLLOW_THIS_in_en_accesspermission_typestates2150() {
        Cloner cloner = new Cloner();
        FOLLOW_THIS_in_en_accesspermission_typestates2150 = cloner.deepClone(FOLLOW_THIS_in_en_accesspermission_typestates2150);
        return FOLLOW_THIS_in_en_accesspermission_typestates2150;
    }

    public static BitSet getFOLLOW_ID_in_usevalue1815() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_usevalue1815 = cloner.deepClone(FOLLOW_ID_in_usevalue1815);
        return FOLLOW_ID_in_usevalue1815;
    }

    public static BitSet getFOLLOW_PARAM_in_re_accesspermission_typestates2011() {
        Cloner cloner = new Cloner();
        FOLLOW_PARAM_in_re_accesspermission_typestates2011 = cloner.deepClone(FOLLOW_PARAM_in_re_accesspermission_typestates2011);
        return FOLLOW_PARAM_in_re_accesspermission_typestates2011;
    }

    public static BitSet getFOLLOW_LESSTHANEQUAL_in_jmlGhostInv1113() {
        Cloner cloner = new Cloner();
        FOLLOW_LESSTHANEQUAL_in_jmlGhostInv1113 = cloner.deepClone(FOLLOW_LESSTHANEQUAL_in_jmlGhostInv1113);
        return FOLLOW_LESSTHANEQUAL_in_jmlGhostInv1113;
    }

    public static BitSet getFOLLOW_state_in_classstates1434() {
        Cloner cloner = new Cloner();
        FOLLOW_state_in_classstates1434 = cloner.deepClone(FOLLOW_state_in_classstates1434);
        return FOLLOW_state_in_classstates1434;
    }

    public static BitSet getFOLLOW_set_in_at_ap_permission0() {
        Cloner cloner = new Cloner();
        FOLLOW_set_in_at_ap_permission0 = cloner.deepClone(FOLLOW_set_in_at_ap_permission0);
        return FOLLOW_set_in_at_ap_permission0;
    }

    public static BitSet getFOLLOW_ID_in_jmlLessThanEqualReq1192() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_jmlLessThanEqualReq1192 = cloner.deepClone(FOLLOW_ID_in_jmlLessThanEqualReq1192);
        return FOLLOW_ID_in_jmlLessThanEqualReq1192;
    }

    public static BitSet getFOLLOW_perm_in_cases1682() {
        Cloner cloner = new Cloner();
        FOLLOW_perm_in_cases1682 = cloner.deepClone(FOLLOW_perm_in_cases1682);
        return FOLLOW_perm_in_cases1682;
    }

    public static BitSet getFOLLOW_ID_in_jmlAssign1293() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_jmlAssign1293 = cloner.deepClone(FOLLOW_ID_in_jmlAssign1293);
        return FOLLOW_ID_in_jmlAssign1293;
    }

    public static BitSet getFOLLOW_PUNCTUATION_in_requires_ensures_clause1836() {
        Cloner cloner = new Cloner();
        FOLLOW_PUNCTUATION_in_requires_ensures_clause1836 = cloner.deepClone(FOLLOW_PUNCTUATION_in_requires_ensures_clause1836);
        return FOLLOW_PUNCTUATION_in_requires_ensures_clause1836;
    }

    public static BitSet getFOLLOW_jmlEnsures_in_jmlMethodSpecification1157() {
        Cloner cloner = new Cloner();
        FOLLOW_jmlEnsures_in_jmlMethodSpecification1157 = cloner.deepClone(FOLLOW_jmlEnsures_in_jmlMethodSpecification1157);
        return FOLLOW_jmlEnsures_in_jmlMethodSpecification1157;
    }

    public static BitSet getFOLLOW_start_classstates_in_classstates1432() {
        Cloner cloner = new Cloner();
        FOLLOW_start_classstates_in_classstates1432 = cloner.deepClone(FOLLOW_start_classstates_in_classstates1432);
        return FOLLOW_start_classstates_in_classstates1432;
    }

    public static BitSet getFOLLOW_requires_clause_in_requires_ensures_clause1831() {
        Cloner cloner = new Cloner();
        FOLLOW_requires_clause_in_requires_ensures_clause1831 = cloner.deepClone(FOLLOW_requires_clause_in_requires_ensures_clause1831);
        return FOLLOW_requires_clause_in_requires_ensures_clause1831;
    }

    public static BitSet getFOLLOW_typestate_in_re_accesspermission_typestates1914() {
        Cloner cloner = new Cloner();
        FOLLOW_typestate_in_re_accesspermission_typestates1914 = cloner.deepClone(FOLLOW_typestate_in_re_accesspermission_typestates1914);
        return FOLLOW_typestate_in_re_accesspermission_typestates1914;
    }

    public static BitSet getFOLLOW_QUOTE_in_attype1765() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_attype1765 = cloner.deepClone(FOLLOW_QUOTE_in_attype1765);
        return FOLLOW_QUOTE_in_attype1765;
    }

    public static BitSet getFOLLOW_IN_in_re_accesspermission_typestates1965() {
        Cloner cloner = new Cloner();
        FOLLOW_IN_in_re_accesspermission_typestates1965 = cloner.deepClone(FOLLOW_IN_in_re_accesspermission_typestates1965);
        return FOLLOW_IN_in_re_accesspermission_typestates1965;
    }

    public static BitSet getFOLLOW_PUNCTUATION_in_value1402() {
        Cloner cloner = new Cloner();
        FOLLOW_PUNCTUATION_in_value1402 = cloner.deepClone(FOLLOW_PUNCTUATION_in_value1402);
        return FOLLOW_PUNCTUATION_in_value1402;
    }

    public static BitSet getFOLLOW_ID_in_re_accesspermission_typestates2015() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_re_accesspermission_typestates2015 = cloner.deepClone(FOLLOW_ID_in_re_accesspermission_typestates2015);
        return FOLLOW_ID_in_re_accesspermission_typestates2015;
    }

    public static BitSet getFOLLOW_ID_in_condition1628() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_condition1628 = cloner.deepClone(FOLLOW_ID_in_condition1628);
        return FOLLOW_ID_in_condition1628;
    }

    public static BitSet getFOLLOW_RSBRACKET_in_re_accesspermission_typestates1962() {
        Cloner cloner = new Cloner();
        FOLLOW_RSBRACKET_in_re_accesspermission_typestates1962 = cloner.deepClone(FOLLOW_RSBRACKET_in_re_accesspermission_typestates1962);
        return FOLLOW_RSBRACKET_in_re_accesspermission_typestates1962;
    }

    public static BitSet getFOLLOW_OPERATOR_in_re_accesspermission_typestates2013() {
        Cloner cloner = new Cloner();
        FOLLOW_OPERATOR_in_re_accesspermission_typestates2013 = cloner.deepClone(FOLLOW_OPERATOR_in_re_accesspermission_typestates2013);
        return FOLLOW_OPERATOR_in_re_accesspermission_typestates2013;
    }

    public static BitSet getFOLLOW_REQUIRES_in_attype1741() {
        Cloner cloner = new Cloner();
        FOLLOW_REQUIRES_in_attype1741 = cloner.deepClone(FOLLOW_REQUIRES_in_attype1741);
        return FOLLOW_REQUIRES_in_attype1741;
    }

    public static BitSet getFOLLOW_ENSURES_in_attype1761() {
        Cloner cloner = new Cloner();
        FOLLOW_ENSURES_in_attype1761 = cloner.deepClone(FOLLOW_ENSURES_in_attype1761);
        return FOLLOW_ENSURES_in_attype1761;
    }

    public static BitSet getFOLLOW_usevalue_in_attype1778() {
        Cloner cloner = new Cloner();
        FOLLOW_usevalue_in_attype1778 = cloner.deepClone(FOLLOW_usevalue_in_attype1778);
        return FOLLOW_usevalue_in_attype1778;
    }

    public static BitSet getFOLLOW_ENSURES_in_jmlEns1263() {
        Cloner cloner = new Cloner();
        FOLLOW_ENSURES_in_jmlEns1263 = cloner.deepClone(FOLLOW_ENSURES_in_jmlEns1263);
        return FOLLOW_ENSURES_in_jmlEns1263;
    }

    public static BitSet getFOLLOW_LSBRACKET_in_jmlOldEns1237() {
        Cloner cloner = new Cloner();
        FOLLOW_LSBRACKET_in_jmlOldEns1237 = cloner.deepClone(FOLLOW_LSBRACKET_in_jmlOldEns1237);
        return FOLLOW_LSBRACKET_in_jmlOldEns1237;
    }

    public static BitSet getFOLLOW_PUNCTUATION_in_states1357() {
        Cloner cloner = new Cloner();
        FOLLOW_PUNCTUATION_in_states1357 = cloner.deepClone(FOLLOW_PUNCTUATION_in_states1357);
        return FOLLOW_PUNCTUATION_in_states1357;
    }

    public static BitSet getFOLLOW_jmlReq_in_jmlRequires1167() {
        Cloner cloner = new Cloner();
        FOLLOW_jmlReq_in_jmlRequires1167 = cloner.deepClone(FOLLOW_jmlReq_in_jmlRequires1167);
        return FOLLOW_jmlReq_in_jmlRequires1167;
    }

    public static BitSet getFOLLOW_PUBLIC_BEHAVIOR_in_jmlMethodSpecification1138() {
        Cloner cloner = new Cloner();
        FOLLOW_PUBLIC_BEHAVIOR_in_jmlMethodSpecification1138 = cloner.deepClone(FOLLOW_PUBLIC_BEHAVIOR_in_jmlMethodSpecification1138);
        return FOLLOW_PUBLIC_BEHAVIOR_in_jmlMethodSpecification1138;
    }

    public static BitSet getFOLLOW_LSBRACKET_in_en_accesspermission_typestates2148() {
        Cloner cloner = new Cloner();
        FOLLOW_LSBRACKET_in_en_accesspermission_typestates2148 = cloner.deepClone(FOLLOW_LSBRACKET_in_en_accesspermission_typestates2148);
        return FOLLOW_LSBRACKET_in_en_accesspermission_typestates2148;
    }

    public static BitSet getFOLLOW_en_accesspermission_typestates_in_ensures_clause1881() {
        Cloner cloner = new Cloner();
        FOLLOW_en_accesspermission_typestates_in_ensures_clause1881 = cloner.deepClone(FOLLOW_en_accesspermission_typestates_in_ensures_clause1881);
        return FOLLOW_en_accesspermission_typestates_in_ensures_clause1881;
    }

    public static BitSet getFOLLOW_jmlEns_in_jmlEnsures1221() {
        Cloner cloner = new Cloner();
        FOLLOW_jmlEns_in_jmlEnsures1221 = cloner.deepClone(FOLLOW_jmlEns_in_jmlEnsures1221);
        return FOLLOW_jmlEns_in_jmlEnsures1221;
    }

    public static BitSet getFOLLOW_accesspermission_in_re_accesspermission_typestates1954() {
        Cloner cloner = new Cloner();
        FOLLOW_accesspermission_in_re_accesspermission_typestates1954 = cloner.deepClone(FOLLOW_accesspermission_in_re_accesspermission_typestates1954);
        return FOLLOW_accesspermission_in_re_accesspermission_typestates1954;
    }

    public static BitSet getFOLLOW_EQUAL_in_usevalue1811() {
        Cloner cloner = new Cloner();
        FOLLOW_EQUAL_in_usevalue1811 = cloner.deepClone(FOLLOW_EQUAL_in_usevalue1811);
        return FOLLOW_EQUAL_in_usevalue1811;
    }

    public static BitSet getFOLLOW_NUMBERS_in_jmlGhostInv1111() {
        Cloner cloner = new Cloner();
        FOLLOW_NUMBERS_in_jmlGhostInv1111 = cloner.deepClone(FOLLOW_NUMBERS_in_jmlGhostInv1111);
        return FOLLOW_NUMBERS_in_jmlGhostInv1111;
    }

    public static BitSet getFOLLOW_re_accesspermission_typestates_in_requires_clause1863() {
        Cloner cloner = new Cloner();
        FOLLOW_re_accesspermission_typestates_in_requires_clause1863 = cloner.deepClone(FOLLOW_re_accesspermission_typestates_in_requires_clause1863);
        return FOLLOW_re_accesspermission_typestates_in_requires_clause1863;
    }

    public static BitSet getFOLLOW_IN_in_en_accesspermission_typestates2208() {
        Cloner cloner = new Cloner();
        FOLLOW_IN_in_en_accesspermission_typestates2208 = cloner.deepClone(FOLLOW_IN_in_en_accesspermission_typestates2208);
        return FOLLOW_IN_in_en_accesspermission_typestates2208;
    }

    public static BitSet getFOLLOW_requires_ensures_clause_in_perm1716() {
        Cloner cloner = new Cloner();
        FOLLOW_requires_ensures_clause_in_perm1716 = cloner.deepClone(FOLLOW_requires_ensures_clause_in_perm1716);
        return FOLLOW_requires_ensures_clause_in_perm1716;
    }

    public static BitSet getFOLLOW_VALUE_in_usevalue1809() {
        Cloner cloner = new Cloner();
        FOLLOW_VALUE_in_usevalue1809 = cloner.deepClone(FOLLOW_VALUE_in_usevalue1809);
        return FOLLOW_VALUE_in_usevalue1809;
    }

    public static BitSet getFOLLOW_ID_in_condition1620() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_condition1620 = cloner.deepClone(FOLLOW_ID_in_condition1620);
        return FOLLOW_ID_in_condition1620;
    }

    public static BitSet getFOLLOW_jmlClassSpecifications_in_jmlSpecifications1071() {
        Cloner cloner = new Cloner();
        FOLLOW_jmlClassSpecifications_in_jmlSpecifications1071 = cloner.deepClone(FOLLOW_jmlClassSpecifications_in_jmlSpecifications1071);
        return FOLLOW_jmlClassSpecifications_in_jmlSpecifications1071;
    }

    public static BitSet getFOLLOW_LESSTHANEQUAL_in_jmlLessThanEqualReq1194() {
        Cloner cloner = new Cloner();
        FOLLOW_LESSTHANEQUAL_in_jmlLessThanEqualReq1194 = cloner.deepClone(FOLLOW_LESSTHANEQUAL_in_jmlLessThanEqualReq1194);
        return FOLLOW_LESSTHANEQUAL_in_jmlLessThanEqualReq1194;
    }

    public static BitSet getFOLLOW_NUMBERS_in_jmlReq1213() {
        Cloner cloner = new Cloner();
        FOLLOW_NUMBERS_in_jmlReq1213 = cloner.deepClone(FOLLOW_NUMBERS_in_jmlReq1213);
        return FOLLOW_NUMBERS_in_jmlReq1213;
    }

    public static BitSet getFOLLOW_RSBRACKET_in_state1496() {
        Cloner cloner = new Cloner();
        FOLLOW_RSBRACKET_in_state1496 = cloner.deepClone(FOLLOW_RSBRACKET_in_state1496);
        return FOLLOW_RSBRACKET_in_state1496;
    }

    public static BitSet getFOLLOW_QUOTE_in_usevalue1813() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_usevalue1813 = cloner.deepClone(FOLLOW_QUOTE_in_usevalue1813);
        return FOLLOW_QUOTE_in_usevalue1813;
    }

    public static BitSet getFOLLOW_re_accesspermission_typestates_in_requires_clause1858() {
        Cloner cloner = new Cloner();
        FOLLOW_re_accesspermission_typestates_in_requires_clause1858 = cloner.deepClone(FOLLOW_re_accesspermission_typestates_in_requires_clause1858);
        return FOLLOW_re_accesspermission_typestates_in_requires_clause1858;
    }

    public static BitSet getFOLLOW_EQUAL_in_usevalue1794() {
        Cloner cloner = new Cloner();
        FOLLOW_EQUAL_in_usevalue1794 = cloner.deepClone(FOLLOW_EQUAL_in_usevalue1794);
        return FOLLOW_EQUAL_in_usevalue1794;
    }

    public static BitSet getFOLLOW_USEFIELDS_in_usevalue1796() {
        Cloner cloner = new Cloner();
        FOLLOW_USEFIELDS_in_usevalue1796 = cloner.deepClone(FOLLOW_USEFIELDS_in_usevalue1796);
        return FOLLOW_USEFIELDS_in_usevalue1796;
    }

    public static BitSet getFOLLOW_state_in_classstates1439() {
        Cloner cloner = new Cloner();
        FOLLOW_state_in_classstates1439 = cloner.deepClone(FOLLOW_state_in_classstates1439);
        return FOLLOW_state_in_classstates1439;
    }

    public static BitSet getFOLLOW_other_in_cases1685() {
        Cloner cloner = new Cloner();
        FOLLOW_other_in_cases1685 = cloner.deepClone(FOLLOW_other_in_cases1685);
        return FOLLOW_other_in_cases1685;
    }

    public static BitSet getFOLLOW_end_classstates_in_classstates1443() {
        Cloner cloner = new Cloner();
        FOLLOW_end_classstates_in_classstates1443 = cloner.deepClone(FOLLOW_end_classstates_in_classstates1443);
        return FOLLOW_end_classstates_in_classstates1443;
    }

    public static BitSet getFOLLOW_EQUALOPERATOR_in_jmlOldEns1233() {
        Cloner cloner = new Cloner();
        FOLLOW_EQUALOPERATOR_in_jmlOldEns1233 = cloner.deepClone(FOLLOW_EQUALOPERATOR_in_jmlOldEns1233);
        return FOLLOW_EQUALOPERATOR_in_jmlOldEns1233;
    }

    public static BitSet getFOLLOW_jmlReq_in_jmlOrReq1178() {
        Cloner cloner = new Cloner();
        FOLLOW_jmlReq_in_jmlOrReq1178 = cloner.deepClone(FOLLOW_jmlReq_in_jmlOrReq1178);
        return FOLLOW_jmlReq_in_jmlOrReq1178;
    }

    public static BitSet getFOLLOW_QUOTE_in_state1485() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_state1485 = cloner.deepClone(FOLLOW_QUOTE_in_state1485);
        return FOLLOW_QUOTE_in_state1485;
    }

    public static BitSet getFOLLOW_EQUAL_in_attype1763() {
        Cloner cloner = new Cloner();
        FOLLOW_EQUAL_in_attype1763 = cloner.deepClone(FOLLOW_EQUAL_in_attype1763);
        return FOLLOW_EQUAL_in_attype1763;
    }

    public static BitSet getFOLLOW_REQUIRES_in_requires_clause1852() {
        Cloner cloner = new Cloner();
        FOLLOW_REQUIRES_in_requires_clause1852 = cloner.deepClone(FOLLOW_REQUIRES_in_requires_clause1852);
        return FOLLOW_REQUIRES_in_requires_clause1852;
    }

    public static BitSet getFOLLOW_RCBRACKET_in_refine1340() {
        Cloner cloner = new Cloner();
        FOLLOW_RCBRACKET_in_refine1340 = cloner.deepClone(FOLLOW_RCBRACKET_in_refine1340);
        return FOLLOW_RCBRACKET_in_refine1340;
    }

    public static BitSet getFOLLOW_DIM_in_dimension1373() {
        Cloner cloner = new Cloner();
        FOLLOW_DIM_in_dimension1373 = cloner.deepClone(FOLLOW_DIM_in_dimension1373);
        return FOLLOW_DIM_in_dimension1373;
    }

    public static BitSet getFOLLOW_EQUAL_in_ensures_clause1877() {
        Cloner cloner = new Cloner();
        FOLLOW_EQUAL_in_ensures_clause1877 = cloner.deepClone(FOLLOW_EQUAL_in_ensures_clause1877);
        return FOLLOW_EQUAL_in_ensures_clause1877;
    }

    public static BitSet getFOLLOW_OR_in_jmlOrReq1181() {
        Cloner cloner = new Cloner();
        FOLLOW_OR_in_jmlOrReq1181 = cloner.deepClone(FOLLOW_OR_in_jmlOrReq1181);
        return FOLLOW_OR_in_jmlOrReq1181;
    }

    public static BitSet getFOLLOW_set_in_condition1652() {
        Cloner cloner = new Cloner();
        FOLLOW_set_in_condition1652 = cloner.deepClone(FOLLOW_set_in_condition1652);
        return FOLLOW_set_in_condition1652;
    }

    public static BitSet getFOLLOW_item_in_value1404() {
        Cloner cloner = new Cloner();
        FOLLOW_item_in_value1404 = cloner.deepClone(FOLLOW_item_in_value1404);
        return FOLLOW_item_in_value1404;
    }

    public static BitSet getFOLLOW_dimension_in_states1355() {
        Cloner cloner = new Cloner();
        FOLLOW_dimension_in_states1355 = cloner.deepClone(FOLLOW_dimension_in_states1355);
        return FOLLOW_dimension_in_states1355;
    }

    public static BitSet getFOLLOW_accesspermission_in_re_accesspermission_typestates1901() {
        Cloner cloner = new Cloner();
        FOLLOW_accesspermission_in_re_accesspermission_typestates1901 = cloner.deepClone(FOLLOW_accesspermission_in_re_accesspermission_typestates1901);
        return FOLLOW_accesspermission_in_re_accesspermission_typestates1901;
    }

    public static BitSet getFOLLOW_typestate_in_attype1769() {
        Cloner cloner = new Cloner();
        FOLLOW_typestate_in_attype1769 = cloner.deepClone(FOLLOW_typestate_in_attype1769);
        return FOLLOW_typestate_in_attype1769;
    }

    public static BitSet getFOLLOW_LSBRACKET_in_attype1738() {
        Cloner cloner = new Cloner();
        FOLLOW_LSBRACKET_in_attype1738 = cloner.deepClone(FOLLOW_LSBRACKET_in_attype1738);
        return FOLLOW_LSBRACKET_in_attype1738;
    }

    public static BitSet getFOLLOW_RSBRACKET_in_jmlOldEns1241() {
        Cloner cloner = new Cloner();
        FOLLOW_RSBRACKET_in_jmlOldEns1241 = cloner.deepClone(FOLLOW_RSBRACKET_in_jmlOldEns1241);
        return FOLLOW_RSBRACKET_in_jmlOldEns1241;
    }

    public static BitSet getFOLLOW_OLD_in_jmlOldEns1235() {
        Cloner cloner = new Cloner();
        FOLLOW_OLD_in_jmlOldEns1235 = cloner.deepClone(FOLLOW_OLD_in_jmlOldEns1235);
        return FOLLOW_OLD_in_jmlOldEns1235;
    }

    public static BitSet getFOLLOW_invariant_in_state1492() {
        Cloner cloner = new Cloner();
        FOLLOW_invariant_in_state1492 = cloner.deepClone(FOLLOW_invariant_in_state1492);
        return FOLLOW_invariant_in_state1492;
    }

    public static BitSet getFOLLOW_QUOTE_in_ensures_clause1879() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_ensures_clause1879 = cloner.deepClone(FOLLOW_QUOTE_in_ensures_clause1879);
        return FOLLOW_QUOTE_in_ensures_clause1879;
    }

    public static BitSet getFOLLOW_LSBRACKET_in_start_classstates1452() {
        Cloner cloner = new Cloner();
        FOLLOW_LSBRACKET_in_start_classstates1452 = cloner.deepClone(FOLLOW_LSBRACKET_in_start_classstates1452);
        return FOLLOW_LSBRACKET_in_start_classstates1452;
    }

    public static BitSet getFOLLOW_PLUSMINUSOPERATOR_in_jmlOldEns1246() {
        Cloner cloner = new Cloner();
        FOLLOW_PLUSMINUSOPERATOR_in_jmlOldEns1246 = cloner.deepClone(FOLLOW_PLUSMINUSOPERATOR_in_jmlOldEns1246);
        return FOLLOW_PLUSMINUSOPERATOR_in_jmlOldEns1246;
    }

    public static BitSet getFOLLOW_OPERATOR_in_condition1624() {
        Cloner cloner = new Cloner();
        FOLLOW_OPERATOR_in_condition1624 = cloner.deepClone(FOLLOW_OPERATOR_in_condition1624);
        return FOLLOW_OPERATOR_in_condition1624;
    }

    public static BitSet getFOLLOW_LSBRACKET_in_refine1324() {
        Cloner cloner = new Cloner();
        FOLLOW_LSBRACKET_in_refine1324 = cloner.deepClone(FOLLOW_LSBRACKET_in_refine1324);
        return FOLLOW_LSBRACKET_in_refine1324;
    }

    public static BitSet getFOLLOW_LESSTHANEQUAL_in_jmlGhostInv1121() {
        Cloner cloner = new Cloner();
        FOLLOW_LESSTHANEQUAL_in_jmlGhostInv1121 = cloner.deepClone(FOLLOW_LESSTHANEQUAL_in_jmlGhostInv1121);
        return FOLLOW_LESSTHANEQUAL_in_jmlGhostInv1121;
    }

    public static BitSet getFOLLOW_jmlRequires_in_jmlMethodSpecification1144() {
        Cloner cloner = new Cloner();
        FOLLOW_jmlRequires_in_jmlMethodSpecification1144 = cloner.deepClone(FOLLOW_jmlRequires_in_jmlMethodSpecification1144);
        return FOLLOW_jmlRequires_in_jmlMethodSpecification1144;
    }

    public static BitSet getFOLLOW_QUOTE_in_dimension1377() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_dimension1377 = cloner.deepClone(FOLLOW_QUOTE_in_dimension1377);
        return FOLLOW_QUOTE_in_dimension1377;
    }

    public static BitSet getFOLLOW_PUNCTUATION_in_attype1776() {
        Cloner cloner = new Cloner();
        FOLLOW_PUNCTUATION_in_attype1776 = cloner.deepClone(FOLLOW_PUNCTUATION_in_attype1776);
        return FOLLOW_PUNCTUATION_in_attype1776;
    }

    public static BitSet getFOLLOW_USE_in_usevalue1792() {
        Cloner cloner = new Cloner();
        FOLLOW_USE_in_usevalue1792 = cloner.deepClone(FOLLOW_USE_in_usevalue1792);
        return FOLLOW_USE_in_usevalue1792;
    }

    public static BitSet getFOLLOW_LSBRACKET_in_en_accesspermission_typestates2201() {
        Cloner cloner = new Cloner();
        FOLLOW_LSBRACKET_in_en_accesspermission_typestates2201 = cloner.deepClone(FOLLOW_LSBRACKET_in_en_accesspermission_typestates2201);
        return FOLLOW_LSBRACKET_in_en_accesspermission_typestates2201;
    }

    public static BitSet getFOLLOW_EQUAL_in_attype1743() {
        Cloner cloner = new Cloner();
        FOLLOW_EQUAL_in_attype1743 = cloner.deepClone(FOLLOW_EQUAL_in_attype1743);
        return FOLLOW_EQUAL_in_attype1743;
    }

    public static BitSet getFOLLOW_LCBRACKET_in_start_classstates1454() {
        Cloner cloner = new Cloner();
        FOLLOW_LCBRACKET_in_start_classstates1454 = cloner.deepClone(FOLLOW_LCBRACKET_in_start_classstates1454);
        return FOLLOW_LCBRACKET_in_start_classstates1454;
    }

    public static BitSet getFOLLOW_ID_in_jmlEns1265() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_jmlEns1265 = cloner.deepClone(FOLLOW_ID_in_jmlEns1265);
        return FOLLOW_ID_in_jmlEns1265;
    }

    public static BitSet getFOLLOW_EQUAL_in_value1395() {
        Cloner cloner = new Cloner();
        FOLLOW_EQUAL_in_value1395 = cloner.deepClone(FOLLOW_EQUAL_in_value1395);
        return FOLLOW_EQUAL_in_value1395;
    }

    public static BitSet getFOLLOW_RCBRACKET_in_value1408() {
        Cloner cloner = new Cloner();
        FOLLOW_RCBRACKET_in_value1408 = cloner.deepClone(FOLLOW_RCBRACKET_in_value1408);
        return FOLLOW_RCBRACKET_in_value1408;
    }

    public static BitSet getFOLLOW_SEMICOLON_in_jmlGhostDeclaration1098() {
        Cloner cloner = new Cloner();
        FOLLOW_SEMICOLON_in_jmlGhostDeclaration1098 = cloner.deepClone(FOLLOW_SEMICOLON_in_jmlGhostDeclaration1098);
        return FOLLOW_SEMICOLON_in_jmlGhostDeclaration1098;
    }

    public static BitSet getFOLLOW_classstates_in_specifications1313() {
        Cloner cloner = new Cloner();
        FOLLOW_classstates_in_specifications1313 = cloner.deepClone(FOLLOW_classstates_in_specifications1313);
        return FOLLOW_classstates_in_specifications1313;
    }

    public static BitSet getFOLLOW_CLASS_STATES_in_start_classstates1450() {
        Cloner cloner = new Cloner();
        FOLLOW_CLASS_STATES_in_start_classstates1450 = cloner.deepClone(FOLLOW_CLASS_STATES_in_start_classstates1450);
        return FOLLOW_CLASS_STATES_in_start_classstates1450;
    }

    public static BitSet getFOLLOW_PUNCTUATION_in_state1490() {
        Cloner cloner = new Cloner();
        FOLLOW_PUNCTUATION_in_state1490 = cloner.deepClone(FOLLOW_PUNCTUATION_in_state1490);
        return FOLLOW_PUNCTUATION_in_state1490;
    }

    public static BitSet getFOLLOW_REFINE_in_refine1322() {
        Cloner cloner = new Cloner();
        FOLLOW_REFINE_in_refine1322 = cloner.deepClone(FOLLOW_REFINE_in_refine1322);
        return FOLLOW_REFINE_in_refine1322;
    }

    public static BitSet getFOLLOW_typestate_in_attype1749() {
        Cloner cloner = new Cloner();
        FOLLOW_typestate_in_attype1749 = cloner.deepClone(FOLLOW_typestate_in_attype1749);
        return FOLLOW_typestate_in_attype1749;
    }

    public static BitSet getFOLLOW_VALUE_in_value1393() {
        Cloner cloner = new Cloner();
        FOLLOW_VALUE_in_value1393 = cloner.deepClone(FOLLOW_VALUE_in_value1393);
        return FOLLOW_VALUE_in_value1393;
    }

    public static BitSet getFOLLOW_EQUALOPERATOR_in_jmlReq1209() {
        Cloner cloner = new Cloner();
        FOLLOW_EQUALOPERATOR_in_jmlReq1209 = cloner.deepClone(FOLLOW_EQUALOPERATOR_in_jmlReq1209);
        return FOLLOW_EQUALOPERATOR_in_jmlReq1209;
    }

    public static BitSet getFOLLOW_PUNCTUATION_in_classstates1437() {
        Cloner cloner = new Cloner();
        FOLLOW_PUNCTUATION_in_classstates1437 = cloner.deepClone(FOLLOW_PUNCTUATION_in_classstates1437);
        return FOLLOW_PUNCTUATION_in_classstates1437;
    }

    public static BitSet getFOLLOW_ID_in_state1483() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_state1483 = cloner.deepClone(FOLLOW_ID_in_state1483);
        return FOLLOW_ID_in_state1483;
    }

    public static BitSet getFOLLOW_accesspermission_in_en_accesspermission_typestates2252() {
        Cloner cloner = new Cloner();
        FOLLOW_accesspermission_in_en_accesspermission_typestates2252 = cloner.deepClone(FOLLOW_accesspermission_in_en_accesspermission_typestates2252);
        return FOLLOW_accesspermission_in_en_accesspermission_typestates2252;
    }

    public static BitSet getFOLLOW_QUOTE_in_invariant1609() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_invariant1609 = cloner.deepClone(FOLLOW_QUOTE_in_invariant1609);
        return FOLLOW_QUOTE_in_invariant1609;
    }

    public static BitSet getFOLLOW_ENSURES_in_jmlOldEns1229() {
        Cloner cloner = new Cloner();
        FOLLOW_ENSURES_in_jmlOldEns1229 = cloner.deepClone(FOLLOW_ENSURES_in_jmlOldEns1229);
        return FOLLOW_ENSURES_in_jmlOldEns1229;
    }

    public static BitSet getFOLLOW_QUOTE_in_usevalue1817() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_usevalue1817 = cloner.deepClone(FOLLOW_QUOTE_in_usevalue1817);
        return FOLLOW_QUOTE_in_usevalue1817;
    }

    public static BitSet getFOLLOW_INV_in_invariant1591() {
        Cloner cloner = new Cloner();
        FOLLOW_INV_in_invariant1591 = cloner.deepClone(FOLLOW_INV_in_invariant1591);
        return FOLLOW_INV_in_invariant1591;
    }

    public static BitSet getFOLLOW_PUNCTUATION_in_attype1756() {
        Cloner cloner = new Cloner();
        FOLLOW_PUNCTUATION_in_attype1756 = cloner.deepClone(FOLLOW_PUNCTUATION_in_attype1756);
        return FOLLOW_PUNCTUATION_in_attype1756;
    }

    public static BitSet getFOLLOW_ID_in_jmlGhostInv1115() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_jmlGhostInv1115 = cloner.deepClone(FOLLOW_ID_in_jmlGhostInv1115);
        return FOLLOW_ID_in_jmlGhostInv1115;
    }

    public static BitSet getFOLLOW_EQUAL_in_state1477() {
        Cloner cloner = new Cloner();
        FOLLOW_EQUAL_in_state1477 = cloner.deepClone(FOLLOW_EQUAL_in_state1477);
        return FOLLOW_EQUAL_in_state1477;
    }

    public static BitSet getFOLLOW_cases_in_specifications1311() {
        Cloner cloner = new Cloner();
        FOLLOW_cases_in_specifications1311 = cloner.deepClone(FOLLOW_cases_in_specifications1311);
        return FOLLOW_cases_in_specifications1311;
    }

    public static BitSet getFOLLOW_JMLEND_in_jmlMethodSpecification1161() {
        Cloner cloner = new Cloner();
        FOLLOW_JMLEND_in_jmlMethodSpecification1161 = cloner.deepClone(FOLLOW_JMLEND_in_jmlMethodSpecification1161);
        return FOLLOW_JMLEND_in_jmlMethodSpecification1161;
    }

    public static BitSet getFOLLOW_QUOTE_in_item1415() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_item1415 = cloner.deepClone(FOLLOW_QUOTE_in_item1415);
        return FOLLOW_QUOTE_in_item1415;
    }

    public static BitSet getFOLLOW_condition_in_invariant1603() {
        Cloner cloner = new Cloner();
        FOLLOW_condition_in_invariant1603 = cloner.deepClone(FOLLOW_condition_in_invariant1603);
        return FOLLOW_condition_in_invariant1603;
    }

    public static BitSet getFOLLOW_typestate_in_en_accesspermission_typestates2267() {
        Cloner cloner = new Cloner();
        FOLLOW_typestate_in_en_accesspermission_typestates2267 = cloner.deepClone(FOLLOW_typestate_in_en_accesspermission_typestates2267);
        return FOLLOW_typestate_in_en_accesspermission_typestates2267;
    }

    public static BitSet getFOLLOW_AND_in_ensures_clause1884() {
        Cloner cloner = new Cloner();
        FOLLOW_AND_in_ensures_clause1884 = cloner.deepClone(FOLLOW_AND_in_ensures_clause1884);
        return FOLLOW_AND_in_ensures_clause1884;
    }

    public static BitSet getFOLLOW_ID_in_jmlOldEns1231() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_jmlOldEns1231 = cloner.deepClone(FOLLOW_ID_in_jmlOldEns1231);
        return FOLLOW_ID_in_jmlOldEns1231;
    }

    public static BitSet getFOLLOW_SEMICOLON_in_jmlOldEns1254() {
        Cloner cloner = new Cloner();
        FOLLOW_SEMICOLON_in_jmlOldEns1254 = cloner.deepClone(FOLLOW_SEMICOLON_in_jmlOldEns1254);
        return FOLLOW_SEMICOLON_in_jmlOldEns1254;
    }

    public static BitSet getFOLLOW_value_in_states1360() {
        Cloner cloner = new Cloner();
        FOLLOW_value_in_states1360 = cloner.deepClone(FOLLOW_value_in_states1360);
        return FOLLOW_value_in_states1360;
    }

    public static BitSet getFOLLOW_ID_in_condition1665() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_condition1665 = cloner.deepClone(FOLLOW_ID_in_condition1665);
        return FOLLOW_ID_in_condition1665;
    }

    public static BitSet getFOLLOW_LSBRACKET_in_re_accesspermission_typestates1956() {
        Cloner cloner = new Cloner();
        FOLLOW_LSBRACKET_in_re_accesspermission_typestates1956 = cloner.deepClone(FOLLOW_LSBRACKET_in_re_accesspermission_typestates1956);
        return FOLLOW_LSBRACKET_in_re_accesspermission_typestates1956;
    }

    public static BitSet getFOLLOW_QUOTE_in_attype1751() {
        Cloner cloner = new Cloner();
        FOLLOW_QUOTE_in_attype1751 = cloner.deepClone(FOLLOW_QUOTE_in_attype1751);
        return FOLLOW_QUOTE_in_attype1751;
    }

    public static BitSet getFOLLOW_RSBRACKET_in_end_classstates1463() {
        Cloner cloner = new Cloner();
        FOLLOW_RSBRACKET_in_end_classstates1463 = cloner.deepClone(FOLLOW_RSBRACKET_in_end_classstates1463);
        return FOLLOW_RSBRACKET_in_end_classstates1463;
    }

    public static BitSet getFOLLOW_GHOST_in_jmlGhostDeclaration1090() {
        Cloner cloner = new Cloner();
        FOLLOW_GHOST_in_jmlGhostDeclaration1090 = cloner.deepClone(FOLLOW_GHOST_in_jmlGhostDeclaration1090);
        return FOLLOW_GHOST_in_jmlGhostDeclaration1090;
    }

    public static BitSet getFOLLOW_SEMICOLON_in_jmlAssign1296() {
        Cloner cloner = new Cloner();
        FOLLOW_SEMICOLON_in_jmlAssign1296 = cloner.deepClone(FOLLOW_SEMICOLON_in_jmlAssign1296);
        return FOLLOW_SEMICOLON_in_jmlAssign1296;
    }

    public static BitSet getFOLLOW_EQUALOPERATOR_in_jmlEns1267() {
        Cloner cloner = new Cloner();
        FOLLOW_EQUALOPERATOR_in_jmlEns1267 = cloner.deepClone(FOLLOW_EQUALOPERATOR_in_jmlEns1267);
        return FOLLOW_EQUALOPERATOR_in_jmlEns1267;
    }

    public static BitSet getFOLLOW_item_in_value1399() {
        Cloner cloner = new Cloner();
        FOLLOW_item_in_value1399 = cloner.deepClone(FOLLOW_item_in_value1399);
        return FOLLOW_item_in_value1399;
    }

    public static BitSet getFOLLOW_set_in_accesspermission0() {
        Cloner cloner = new Cloner();
        FOLLOW_set_in_accesspermission0 = cloner.deepClone(FOLLOW_set_in_accesspermission0);
        return FOLLOW_set_in_accesspermission0;
    }

    public static BitSet getFOLLOW_NUMBERS_in_jmlOldEns1250() {
        Cloner cloner = new Cloner();
        FOLLOW_NUMBERS_in_jmlOldEns1250 = cloner.deepClone(FOLLOW_NUMBERS_in_jmlOldEns1250);
        return FOLLOW_NUMBERS_in_jmlOldEns1250;
    }

    public static BitSet getFOLLOW_LSBRACKET_in_condition1648() {
        Cloner cloner = new Cloner();
        FOLLOW_LSBRACKET_in_condition1648 = cloner.deepClone(FOLLOW_LSBRACKET_in_condition1648);
        return FOLLOW_LSBRACKET_in_condition1648;
    }

    public static BitSet getFOLLOW_AND_in_invariant1601() {
        Cloner cloner = new Cloner();
        FOLLOW_AND_in_invariant1601 = cloner.deepClone(FOLLOW_AND_in_invariant1601);
        return FOLLOW_AND_in_invariant1601;
    }

    public static BitSet getFOLLOW_THIS_in_re_accesspermission_typestates1905() {
        Cloner cloner = new Cloner();
        FOLLOW_THIS_in_re_accesspermission_typestates1905 = cloner.deepClone(FOLLOW_THIS_in_re_accesspermission_typestates1905);
        return FOLLOW_THIS_in_re_accesspermission_typestates1905;
    }

    public static BitSet getFOLLOW_NUMBERS_in_jmlGhostInv1125() {
        Cloner cloner = new Cloner();
        FOLLOW_NUMBERS_in_jmlGhostInv1125 = cloner.deepClone(FOLLOW_NUMBERS_in_jmlGhostInv1125);
        return FOLLOW_NUMBERS_in_jmlGhostInv1125;
    }

    public static BitSet getFOLLOW_ID_in_en_accesspermission_typestates2313() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_en_accesspermission_typestates2313 = cloner.deepClone(FOLLOW_ID_in_en_accesspermission_typestates2313);
        return FOLLOW_ID_in_en_accesspermission_typestates2313;
    }

    public static BitSet getFOLLOW_jmlGhostDeclaration_in_jmlClassSpecifications1081() {
        Cloner cloner = new Cloner();
        FOLLOW_jmlGhostDeclaration_in_jmlClassSpecifications1081 = cloner.deepClone(FOLLOW_jmlGhostDeclaration_in_jmlClassSpecifications1081);
        return FOLLOW_jmlGhostDeclaration_in_jmlClassSpecifications1081;
    }

    public static BitSet getFOLLOW_RSBRACKET_in_re_accesspermission_typestates1907() {
        Cloner cloner = new Cloner();
        FOLLOW_RSBRACKET_in_re_accesspermission_typestates1907 = cloner.deepClone(FOLLOW_RSBRACKET_in_re_accesspermission_typestates1907);
        return FOLLOW_RSBRACKET_in_re_accesspermission_typestates1907;
    }

    public static BitSet getFOLLOW_typestate_in_en_accesspermission_typestates2212() {
        Cloner cloner = new Cloner();
        FOLLOW_typestate_in_en_accesspermission_typestates2212 = cloner.deepClone(FOLLOW_typestate_in_en_accesspermission_typestates2212);
        return FOLLOW_typestate_in_en_accesspermission_typestates2212;
    }

    public static BitSet getFOLLOW_ID_in_jmlReq1207() {
        Cloner cloner = new Cloner();
        FOLLOW_ID_in_jmlReq1207 = cloner.deepClone(FOLLOW_ID_in_jmlReq1207);
        return FOLLOW_ID_in_jmlReq1207;
    }

    public static BitSet getFOLLOW_RCBRACKET_in_cases1691() {
        Cloner cloner = new Cloner();
        FOLLOW_RCBRACKET_in_cases1691 = cloner.deepClone(FOLLOW_RCBRACKET_in_cases1691);
        return FOLLOW_RCBRACKET_in_cases1691;
    }
} 