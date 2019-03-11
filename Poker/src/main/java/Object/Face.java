package Object;

public enum Face {

	
	TWO('2'){
        public String toString() {
            return "TWO";
        }}, 
	THREE('3'){
        public String toString() {
            return "THREE";
        }}, 
	FOUR('4'){
        public String toString() {
            return "FOUR";
        }}, 
	FIVE('5'){
        public String toString() {
            return "FIVE";
        }}, 
	SIX('6'){
        public String toString() {
            return "SIX";
        }}, 
	SEVEN('7'){
        public String toString() {
            return "SEVEN";
        }}, 
	EIGHT('8'){
        public String toString() {
            return "EIGHT";
        }}, 
	NINE('9'){
        public String toString() {
            return "NINE";
        }}, 
	TEN('T'){
        public String toString() {
            return "TEN";
        }}, 
	JACK('J'){
        public String toString() {
            return "JACK";
        }}, 
	QUEEN('Q'){
        public String toString() {
            return "QUEEN";
        }}, 
	KING('K'){
        public String toString() {
            return "KING";
        }},
	ACE('A'){
        public String toString() {
            return "ACE";
        }};
	
	
        private char symbol;

        private Face(char symbol)
        {
            this.symbol = symbol;
        }

        public char getSymbol()
        {
            return this.symbol;
        }
}
