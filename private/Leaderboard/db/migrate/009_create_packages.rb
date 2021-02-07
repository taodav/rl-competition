class CreatePackages < ActiveRecord::Migration
  def self.up
    create_table :packages do |t|
      t.column "revision", :integer
      t.column "created_at", :datetime
      
      t.column "content_type", :string
      t.column "filename", :string     
      t.column "size", :integer
      
      # used with thumbnails, always required
      t.column "parent_id",  :integer 
      t.column "thumbnail", :string
    end
  end

  def self.down
    drop_table :packages
  end
end
