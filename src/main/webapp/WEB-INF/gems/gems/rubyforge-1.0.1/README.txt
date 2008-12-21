= Rubyforge

* http://codeforpeople.rubyforge.org/rubyforge/
* http://rubyforge.org/projects/codeforpeople/

== Description

A script which automates a limited set of rubyforge operations.

* Run 'rubyforge help' for complete usage.
* Setup: For first time users AND upgrades to 0.4.0:
  * rubyforge setup (deletes your username and password, so run sparingly!)
  * edit ~/.rubyforge/user-config.yml
  * rubyforge config
* For all rubyforge upgrades, run 'rubyforge config' to ensure you have latest.
* Don't forget to login!  logging in will store a cookie in your
  .rubyforge directory which expires after a time.  always run the
  login command before any operation that requires authentication,
  such as uploading a package.

== Synopsis

  rubyforge [options]* mode [mode_args]*

