require 'rake'

# default rake task
task :default => :test

task :compile do
  system 'cd src && make'
end

task :test => :compile do
  9.times do |i|
    puts `cd src && java MST -a 1 -t 8 -n 50 -s #{i}`
  end
end

