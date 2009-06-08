
require 'liquid'

class Content < ActiveRecord::Base
  belongs_to :last_revision, :class_name => 'Revision', :foreign_key => 'lastRevision_id'
  belongs_to :container

  def to_liquid
    return ContentDrop.new(self)
  end
end

class ContentDrop < Liquid::Drop
  def initialize(content)
    @content = content
  end

  def title
    @content.last_revision.title
  end

  def body
    @content.last_revision.body
  end

  def group_short_name
    @content.container.owner.shortName
  end

  def statetoken
    @content.container.owner.shortName + "." + @content.container.toolName + "." + @content.container.id.to_s + "." +  @content.id.to_s
  end

end
