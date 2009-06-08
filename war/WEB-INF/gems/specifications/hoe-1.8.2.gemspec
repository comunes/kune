# -*- encoding: utf-8 -*-

Gem::Specification.new do |s|
  s.name = %q{hoe}
  s.version = "1.8.2"

  s.required_rubygems_version = Gem::Requirement.new(">= 0") if s.respond_to? :required_rubygems_version=
  s.authors = ["Ryan Davis"]
  s.date = %q{2008-10-24}
  s.default_executable = %q{sow}
  s.description = %q{Hoe is a simple rake/rubygems helper for project Rakefiles. It generates all the usual tasks for projects including rdoc generation, testing, packaging, and deployment.  Tasks Provided:  * announce         - Create news email file and post to rubyforge. * audit            - Run ZenTest against the package. * check_manifest   - Verify the manifest. * clean            - Clean up all the extras. * config_hoe       - Create a fresh ~/.hoerc file. * debug_gem        - Show information about the gem. * default          - Run the default tasks. * deps:email       - Print a contact list for gems dependent on this gem * deps:fetch       - Fetch all the dependent gems of this gem into tarballs * deps:list        - List all the dependent gems of this gem * docs             - Build the docs HTML Files * email            - Generate email announcement file. * gem              - Build the gem file hoe-1.8.0.gem * generate_key     - Generate a key for signing your gems. * install_gem      - Install the package as a gem. * multi            - Run the test suite using multiruby. * package          - Build all the packages * post_blog        - Post announcement to blog. * post_news        - Post announcement to rubyforge. * publish_docs     - Publish RDoc to RubyForge. * release          - Package and upload the release to rubyforge. * ridocs           - Generate ri locally for testing. * tasks            - Generate a list of tasks for doco. * test             - Run the test suite. * test_deps        - Show which test files fail when run alone.  See class rdoc for help. Hint: ri Hoe}
  s.email = ["ryand-ruby@zenspider.com"]
  s.executables = ["sow"]
  s.extra_rdoc_files = ["History.txt", "Manifest.txt", "README.txt"]
  s.files = ["History.txt", "Manifest.txt", "README.txt", "Rakefile", "bin/sow", "lib/hoe.rb", "test/test_hoe.rb"]
  s.has_rdoc = true
  s.homepage = %q{http://rubyforge.org/projects/seattlerb/}
  s.rdoc_options = ["--main", "README.txt"]
  s.require_paths = ["lib"]
  s.rubyforge_project = %q{seattlerb}
  s.rubygems_version = %q{1.3.1}
  s.summary = %q{Hoe is a simple rake/rubygems helper for project Rakefiles}
  s.test_files = ["test/test_hoe.rb"]

  if s.respond_to? :specification_version then
    current_version = Gem::Specification::CURRENT_SPECIFICATION_VERSION
    s.specification_version = 2

    if Gem::Version.new(Gem::RubyGemsVersion) >= Gem::Version.new('1.2.0') then
      s.add_runtime_dependency(%q<rubyforge>, [">= 1.0.1"])
      s.add_runtime_dependency(%q<rake>, [">= 0.8.3"])
    else
      s.add_dependency(%q<rubyforge>, [">= 1.0.1"])
      s.add_dependency(%q<rake>, [">= 0.8.3"])
    end
  else
    s.add_dependency(%q<rubyforge>, [">= 1.0.1"])
    s.add_dependency(%q<rake>, [">= 0.8.3"])
  end
end
