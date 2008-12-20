class DocumentsController < ApplicationController
  def index
    @contents = Content.find :all
  end
end
