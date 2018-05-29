require 'pry'

class FunArray
	attr_accessor :fun_array

	def initialize *fun
		@fun_array = Array.new
		for elem in fun
			@fun_array << elem
		end
	end

	def fun_select
		counter = 0
		results = Array.new
		while counter < @fun_array.length
			if yield(fun_array[counter])
				results << fun_array[counter]
			end
			counter += 1
		end
		f = FunArray.new
		f.fun_array = results
		f
	end

	def fun_map
		counter = 0
		results = Array.new
		while counter < @fun_array.length
			results << yield(fun_array[counter])
			counter += 1
		end
		f = FunArray.new
		f.fun_array = results
		f
	end

	def fun_reduce
		counter = 1
		result = fun_array[0]
		while counter < @fun_array.length
			result = yield(result, fun_array[counter])
			counter += 1
		end
		result
	end

end

a = FunArray.new(45, 35, 74, 21, 57, 42, 98, 101)
b = FunArray.new("this", 30, "is", 22, "a", "good", "idea")
c = FunArray.new("this", "is", "a", "good", "idea")
binding.pry