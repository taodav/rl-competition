class MoveResultsToResultFiles < ActiveRecord::Migration
  def self.up
    drop_table :results
    create_table :result_files do |t|
      t.column "content_type", :string
      t.column "filename", :string     
      t.column "size", :integer
      
      # used with thumbnails, always required
      t.column "parent_id",  :integer 
      t.column "thumbnail", :string
    end
  end

  def self.down
    drop_table :result_files
    create_table :results do |t|
      t.column "content_type", :string
      t.column "filename", :string     
      t.column "size", :integer
      
      # used with thumbnails, always required
      t.column "parent_id",  :integer 
      t.column "thumbnail", :string
    end
  end
end
