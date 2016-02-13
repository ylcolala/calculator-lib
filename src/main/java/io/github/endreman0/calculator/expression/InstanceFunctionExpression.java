package io.github.endreman0.calculator.expression;

import java.lang.reflect.Method;

import io.github.endreman0.calculator.expression.type.Type;
import io.github.endreman0.calculator.util.ReflectionUtils;
import io.github.endreman0.calculator.util.Utility;

public class InstanceFunctionExpression extends Expression{
	private Expression obj;
	private Expression[] args;
	private String function;
	public InstanceFunctionExpression(Expression obj, String function, Expression... args){
		this.obj = obj; this.function = function; this.args = args;
	}
	protected Type eval() throws ReflectiveOperationException{
		Type object = obj.evaluate();
		Type[] arguments = new Type[args.length];
		for(int i=0; i<args.length; i++) arguments[i] = args[i].evaluate();
		Method m = ReflectionUtils.instanceFunction(object, function, arguments);
		return m == null ? null : Utility.wrap(m.invoke(object, (Object[])arguments));
	}

	@Override
	public String toParseableString(){
		StringBuilder sb = new StringBuilder(obj.toParseableString()).append('.').append(function).append('(');
		for(int i=0; i<args.length; i++){
			sb.append(args[i].toParseableString());
			if(i+1 < args.length) sb.append(", ");
		}
		return sb.append(')').toString();
	}

	@Override
	public String toDisplayString(){
		StringBuilder sb = new StringBuilder(obj.toDisplayString()).append('.').append(function).append('(');
		for(int i=0; i<args.length; i++){
			sb.append(args[i].toDisplayString());
			if(i+1 < args.length) sb.append(", ");
		}
		return sb.append(')').toString();
	}

	@Override
	public String toDescriptorString(){
		StringBuilder sb = new StringBuilder("InstanceFunction[").append(obj.toDescriptorString()).append(',').append(function).append(',');
		for(int i=0; i<args.length; i++){
			sb.append(args[i].toDescriptorString());
			if(i+1 < args.length) sb.append(',');
		}
		return sb.append(']').toString();
	}
}