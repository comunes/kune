
require 'liquid'

module KuneTemplates
  FILE_ROOT = "#{RAILS_ROOT}/public/templates"
  URL_ROOT = "/templates"
end

module KuneDrops
  class DocumentDrop < Liquid::Drop
    
  end
end

module KuneTags
  class Asset < Liquid::Tag
    def initialize(tag_name, name, tokens)
      super
      @name = name
    end

    def render(context)
      %Q(<link href="#{context['url_root']}/#{@name}" media="screen" rel="stylesheet" type="text/css" />)
    end
  end
end

module KuneFilters
  include KuneTemplates

  #FIXME: inject the template name!!!!!!!!!!
  def asset(name)
    "#{URL_ROOT}/basic/#{name}"
  end
end

class Templater
  include KuneTemplates
  
  def initialize
    Liquid::Template.register_filter(KuneFilters)
    Liquid::Template.register_tag('asset', KuneTags::Asset)
    @parsed = {}
  end

  def render(template, tool, ctx)
    file = "#{FILE_ROOT}/#{template}/#{tool}.liquid.html"
    tmpl = File.read file
    parsed = Liquid::Template.parse(tmpl)
    parsed.render({"url_root" => "#{URL_ROOT}/#{template}"}.merge ctx)
  end
end
