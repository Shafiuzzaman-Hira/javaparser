package me.tomassetti.symbolsolver.model.javaparser.contexts;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.google.common.collect.ImmutableList;
import me.tomassetti.symbolsolver.javaparser.Navigator;
import me.tomassetti.symbolsolver.model.AbstractTest;
import me.tomassetti.symbolsolver.model.Context;
import me.tomassetti.symbolsolver.model.SymbolReference;
import me.tomassetti.symbolsolver.model.Value;
import me.tomassetti.symbolsolver.model.declarations.*;
import me.tomassetti.symbolsolver.model.reflection.ReflectionClassDeclaration;
import me.tomassetti.symbolsolver.model.typesolvers.DummyTypeSolver;
import me.tomassetti.symbolsolver.model.typesolvers.JreTypeSolver;
import me.tomassetti.symbolsolver.model.usages.*;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Federico Tomassetti
 */
public class EnumDeclarationContextTest extends AbstractTest {

    @Test
    public void solveSymbolReferringToDeclaredInstanceField() throws ParseException {
        CompilationUnit cu = parseSample("AnEnum");
        com.github.javaparser.ast.body.EnumDeclaration enumDeclaration = Navigator.demandEnum(cu, "MyEnum");
        Context context = new EnumDeclarationContext(enumDeclaration);

        SymbolReference<? extends ValueDeclaration> ref = context.solveSymbol("i", new DummyTypeSolver());
        assertEquals(true, ref.isSolved());
        assertEquals("int", ref.getCorrespondingDeclaration().getType(new DummyTypeSolver()).getTypeName());
    }

    @Test
    public void solveSymbolReferringToDeclaredStaticField() throws ParseException {
        CompilationUnit cu = parseSample("AnEnum");
        com.github.javaparser.ast.body.EnumDeclaration enumDeclaration = Navigator.demandEnum(cu, "MyEnum");
        Context context = new EnumDeclarationContext(enumDeclaration);

        SymbolReference<? extends ValueDeclaration> ref = context.solveSymbol("j", new DummyTypeSolver());
        assertEquals(true, ref.isSolved());
        assertEquals("long", ref.getCorrespondingDeclaration().getType(new DummyTypeSolver()).getTypeName());
    }

    @Test
    public void solveSymbolReferringToValue() throws ParseException {
        CompilationUnit cu = parseSample("AnEnum");
        com.github.javaparser.ast.body.EnumDeclaration enumDeclaration = Navigator.demandEnum(cu, "MyEnum");
        Context context = new EnumDeclarationContext(enumDeclaration);

        SymbolReference<? extends ValueDeclaration> ref = context.solveSymbol("E1", new DummyTypeSolver());
        assertEquals(true, ref.isSolved());
        assertEquals("MyEnum", ref.getCorrespondingDeclaration().getType(new DummyTypeSolver()).getTypeName());
    }

    @Test
    public void solveSymbolAsValueReferringToDeclaredInstanceField() throws ParseException {
        CompilationUnit cu = parseSample("AnEnum");
        com.github.javaparser.ast.body.EnumDeclaration enumDeclaration = Navigator.demandEnum(cu, "MyEnum");
        Context context = new EnumDeclarationContext(enumDeclaration);

        Optional<Value> ref = context.solveSymbolAsValue("i", new DummyTypeSolver());
        assertEquals(true, ref.isPresent());
        assertEquals("int", ref.get().getUsage().getTypeName());
    }

    @Test
    public void solveSymbolAsValueReferringToDeclaredStaticField() throws ParseException {
        CompilationUnit cu = parseSample("AnEnum");
        com.github.javaparser.ast.body.EnumDeclaration enumDeclaration = Navigator.demandEnum(cu, "MyEnum");
        Context context = new EnumDeclarationContext(enumDeclaration);

        Optional<Value> ref = context.solveSymbolAsValue("j", new DummyTypeSolver());
        assertEquals(true, ref.isPresent());
        assertEquals("long", ref.get().getUsage().getTypeName());
    }

    @Test
    public void solveSymbolAsValueReferringToValue() throws ParseException {
        CompilationUnit cu = parseSample("AnEnum");
        com.github.javaparser.ast.body.EnumDeclaration enumDeclaration = Navigator.demandEnum(cu, "MyEnum");
        Context context = new EnumDeclarationContext(enumDeclaration);

        Optional<Value> ref = context.solveSymbolAsValue("E1", new DummyTypeSolver());
        assertEquals(true, ref.isPresent());
        assertEquals("MyEnum", ref.get().getUsage().getTypeName());
    }

}
