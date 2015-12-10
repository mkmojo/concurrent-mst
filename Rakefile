require 'rake'

# default rake task
task :default => :test

task :compile do
  system 'cd src && make'
end

task :test => :compile do
  10.times do |i|
    puts "Seed #{i}: " +
      `cd src && java MST -a 1 -t 8 -n 50 -s #{i} | sed -ne '/elapsed.*/p'`
  end
end

