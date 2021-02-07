class CreateSummaryResults < ActiveRecord::Migration
  def self.up
    create_table :summary_results do |t|
      t.column :team_key_id,    :integer
      t.column :number_of_mdps, :integer
      t.column :episodes,       :integer
      t.column :total_return,   :float
      t.column :total_steps,    :integer
      t.column :created_at,     :datetime
    end
  end

  def self.down
    drop_table :summary_results
  end
end
