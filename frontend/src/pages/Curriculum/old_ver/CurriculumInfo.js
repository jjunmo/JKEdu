import styled from "styled-components";

const Content = styled.div`
  box-sizing: border-box;
  width: 600px;
  margin: 15px auto;
  border: 2px solid black;
  font-family: SCDream5;
`;

const CurriculumHeader = styled.div`
  display: flex;
`;
const SpeakingCurriculum = styled(CurriculumHeader)``;
const ReadingCurriculum = styled(CurriculumHeader)``;
const WritingCurriculum = styled(CurriculumHeader)``;
const ListeningCurriculum = styled(CurriculumHeader)``;

// 좌측상단의 빈 div
const DivideDiv = styled.div`
  box-sizing: border-box;
  width: 150px;
  height: 30px;
  text-align: center;
  border-right: 2px solid gray;
  border-bottom: 2px solid gray;
`;
// 몇 개월을 나타내는 div
const MonthDiv = styled(DivideDiv)`
  line-height: 30px;
`;

// 영역을 나타내는 div
const PartDiv = styled(DivideDiv)`
  display: flex;
  height: 200px;
  align-items: center;
  justify-content: center;
  font-size: 30px;
`;

// 각 영역별 커리큘럼에 맞는 책을 보여주는 div
const CurriculumDiv = styled(PartDiv)``;
const BookImg = styled.img`
  width: 140px;
  height: 180px;
`;

const CurriculumInfo = () => {
  return (
    <Content>
      <CurriculumHeader>
        <DivideDiv></DivideDiv>
        <MonthDiv>1</MonthDiv>
        <MonthDiv>2</MonthDiv>
        <MonthDiv>3</MonthDiv>
      </CurriculumHeader>
      <WritingCurriculum>
        <PartDiv>Writing</PartDiv>
        <CurriculumDiv>
          <BookImg src="http://image.kyobobook.co.kr/images/book/xlarge/595/x9788945092595.jpg"></BookImg>
        </CurriculumDiv>
        <CurriculumDiv>
          <BookImg src="http://image.kyobobook.co.kr/images/book/xlarge/601/x9788945092601.jpg"></BookImg>
        </CurriculumDiv>
        <CurriculumDiv>
          <BookImg src="http://image.kyobobook.co.kr/images/book/xlarge/033/x9788993306033.jpg"></BookImg>
        </CurriculumDiv>
      </WritingCurriculum>
      <ListeningCurriculum>
        <PartDiv>Listening</PartDiv>
        <CurriculumDiv>
          <BookImg src="https://bimage.interpark.com/partner/goods_image/2/2/6/5/210522265g.jpg"></BookImg>
        </CurriculumDiv>
        <CurriculumDiv>
          <BookImg src="http://image.kyobobook.co.kr/images/book/xlarge/842/x9788993306842.jpg"></BookImg>
        </CurriculumDiv>
        <CurriculumDiv>
          <BookImg src="http://image.kyobobook.co.kr/images/book/xlarge/859/x9788993306859.jpg"></BookImg>
        </CurriculumDiv>
      </ListeningCurriculum>
      <SpeakingCurriculum>
        <PartDiv>Speaking</PartDiv>
        <CurriculumDiv>
          <BookImg src="http://image.kyobobook.co.kr/images/book/xlarge/809/x9788966530809.jpg"></BookImg>
        </CurriculumDiv>
        <CurriculumDiv>
          <BookImg src="https://image.yes24.com/goods/45018578/XL"></BookImg>
        </CurriculumDiv>
        <CurriculumDiv>
          <BookImg src="https://image.yes24.com/goods/2988595/XL"></BookImg>
        </CurriculumDiv>
      </SpeakingCurriculum>
      <ReadingCurriculum>
        <PartDiv>Reading</PartDiv>
        <CurriculumDiv>
          <BookImg src="http://image.kyobobook.co.kr/images/book/xlarge/355/x9791188228355.jpg"></BookImg>
        </CurriculumDiv>
        <CurriculumDiv>
          <BookImg src="http://image.kyobobook.co.kr/images/book/xlarge/362/x9791188228362.jpg"></BookImg>
        </CurriculumDiv>
        <CurriculumDiv>
          <BookImg src="http://image.kyobobook.co.kr/images/book/large/379/l9791188228379.jpg"></BookImg>
        </CurriculumDiv>
      </ReadingCurriculum>
    </Content>
  );
};

export default CurriculumInfo;
